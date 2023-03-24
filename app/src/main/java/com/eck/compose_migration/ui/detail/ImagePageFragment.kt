package com.eck.compose_migration.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.eck.compose_migration.databinding.FragmentImagePageBinding
import com.eck.compose_migration.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagePageFragment : BaseFragment<FragmentImagePageBinding>() {

    var onItemClick: (() -> Unit)? = null

    override fun setBinding(layoutInflater: LayoutInflater, container: ViewGroup?): FragmentImagePageBinding {
        return FragmentImagePageBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context ?: return
        arguments?.getString(URL)?.let { url ->
            Glide.with(context).load(url).into(binding.image)
        }
        binding.image.setOnClickListener {
            onItemClick?.invoke()
        }
    }

    companion object {
        private const val URL = "url"

        fun newInstance(url: String) =
            ImagePageFragment().apply {
                arguments = Bundle().apply {
                    putString(URL, url)
                }
            }
    }
}