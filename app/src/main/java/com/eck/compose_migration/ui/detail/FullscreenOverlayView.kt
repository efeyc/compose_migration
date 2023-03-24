package com.eck.compose_migration.ui.detail

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.eck.compose_migration.databinding.ViewFullscreenOverlayBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullscreenOverlayView(context: Context) : ConstraintLayout(context) {

    private var binding = ViewFullscreenOverlayBinding.inflate(LayoutInflater.from(context), this, true)

    fun initView(imageCount: Int, onClosed: () -> Unit) {

        for (i in 0 until imageCount) {
            val newTab = binding.tabLayout.newTab()
            newTab.view.isClickable = false
            binding.tabLayout.addTab(newTab)
        }
        binding.btnClose.setOnClickListener {
            onClosed()
        }
    }

    fun setCurrentPosition(position: Int) {
        binding.tabLayout.getTabAt(position)?.select()
    }
}
