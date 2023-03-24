package com.eck.compose_migration.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.eck.compose_migration.databinding.FragmentSortBinding
import com.eck.compose_migration.ui.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortFragment : BaseBottomSheetDialogFragment<FragmentSortBinding>() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun setBinding(layoutInflater: LayoutInflater, container: ViewGroup?): FragmentSortBinding {
        return FragmentSortBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.apply {

            binding.recyclerView.adapter = SortAdapter(mainViewModel.getSelectedSort()) { sortType ->
                mainViewModel.setSelectedSort(sortType)
            }

            btnClose.setOnClickListener {
                this@SortFragment.dismiss()
            }
        }
    }
}
