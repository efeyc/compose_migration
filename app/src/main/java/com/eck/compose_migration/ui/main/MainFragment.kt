package com.eck.compose_migration.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eck.compose_migration.databinding.FragmentMainBinding
import com.eck.compose_migration.ui.base.BaseActivity
import com.eck.compose_migration.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun setBinding(layoutInflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        val adapter = MainAdapter { uiAdvert ->
            findNavController().navigate(MainFragmentDirections.openDetail(uiAdvert))
        }

        binding.recyclerView.adapter = adapter
        binding.editSearch.addTextChangedListener { text ->
            if (text?.isNotEmpty() == true) {
                mainViewModel.searchData(text.toString())
            } else {
                mainViewModel.fetchData()
            }
        }

        binding.btnSort.setOnClickListener {
            (activity as? BaseActivity)?.showDialogFragment(SortFragment()) {
                mainViewModel.fetchData()
            }
        }

        binding.btnFilter.setOnClickListener {
            (activity as? BaseActivity)?.showDialogFragment(FilterFragment()) {
                mainViewModel.fetchData()
            }
        }
    }

    private fun initViewModel() {
        mainViewModel.fetchData()
        mainViewModel.getData().observe(viewLifecycleOwner) { ads ->
            (binding.recyclerView.adapter as? MainAdapter)?.setData(ads)
            binding.emptyView.isVisible = ads.isEmpty()
        }
    }
}