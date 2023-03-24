package com.eck.compose_migration.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ImagePagerAdapter(
    fa: FragmentActivity, private val images: List<String>,
    private val onItemClick: () -> Unit
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = images.size

    override fun createFragment(position: Int): Fragment {
        val fragment = ImagePageFragment.newInstance(images[position])
        fragment.onItemClick = onItemClick
        return fragment
    }
}