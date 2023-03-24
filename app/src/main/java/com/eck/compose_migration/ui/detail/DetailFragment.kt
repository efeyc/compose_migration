package com.eck.compose_migration.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.eck.compose_migration.R
import com.eck.compose_migration.databinding.FragmentDetailBinding
import com.eck.compose_migration.ui.base.BaseActivity
import com.eck.compose_migration.ui.base.BaseFragment
import com.eck.compose_migration.ui.main.MainViewModel
import com.eck.compose_migration.utils.openDialer
import com.stfalcon.imageviewer.StfalconImageViewer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val mainViewModel: MainViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun setBinding(layoutInflater: LayoutInflater, container: ViewGroup?): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {

        (activity as? BaseActivity)?.setToolbar(binding.toolbar)
        initImages()

        binding.description.text = args.advert.description

        binding.btnCall.isVisible = args.advert.phone != null
        binding.btnCall.setOnClickListener {
            context?.openDialer(args.advert.phone.toString())
        }

        binding.btnBookmark.setOnClickListener {
            mainViewModel.toggleBookmark(args.advert.id)
            setBookmarkIcon()
        }

        setBookmarkIcon()
        initDetails()
    }

    @SuppressLint("SetTextI18n")
    private fun initImages() {
        val context = context ?: return
        val images = args.advert.images

        val fullscreenOverlayView = FullscreenOverlayView(context)
        val imageViewer = StfalconImageViewer.Builder(context, images) { view, image ->
            Glide.with(context).load(image.replace("\n", "")).into(view)
        }.withOverlayView(fullscreenOverlayView)
            .withImageChangeListener { position ->
                fullscreenOverlayView.setCurrentPosition(position)
            }.build()
        fullscreenOverlayView.initView(images.size) {
            imageViewer.dismiss()
        }

        activity?.let {
            binding.viewPager.adapter = ImagePagerAdapter(it, images) {
                val position = binding.viewPager.currentItem
                imageViewer.setCurrentPosition(position)
                fullscreenOverlayView.setCurrentPosition(position)
                imageViewer.show()
            }
        }
        binding.pageLabel.isVisible = images.isNotEmpty()
        binding.pageLabel.text = "1 / ${images.size}"
        binding.viewPager.isVisible = images.isNotEmpty()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.pageLabel.text = "${position + 1} / ${args.advert.images.size}"
            }
        })
    }

    private fun setBookmarkIcon() {
        val bookmarkIcon = if (mainViewModel.isSavedAd(args.advert.id)) R.drawable.ic_saved else R.drawable.ic_unsaved
        binding.btnBookmark.setImageResource(bookmarkIcon)
    }

    private fun initDetails() {
        val details = ArrayList<Pair<String, String>>()
        details.add(Pair(getString(R.string.make), args.advert.make))
        details.add(Pair(getString(R.string.model), args.advert.model))
        args.advert.modelLine?.let {
            details.add(Pair(getString(R.string.modelline), it))
        }
        details.add(Pair(getString(R.string.price), "${args.advert.price} €"))
        args.advert.firstRegistration?.let {
            details.add(Pair(getString(R.string.first_registration), it))
        }
        args.advert.colour?.let {
            details.add(Pair(getString(R.string.colour), it))
        }
        details.add(Pair(getString(R.string.mileage), args.advert.mileage.toString()))
        details.add(Pair(getString(R.string.fuel), args.advert.fuel))
        args.advert.sellerType?.let {
            details.add(Pair(getString(R.string.seller_type), it))
        }
        args.advert.city?.let {
            details.add(Pair(getString(R.string.city), it))
        }

        binding.recyclerView.adapter = DetailAdapter(details)
    }

}