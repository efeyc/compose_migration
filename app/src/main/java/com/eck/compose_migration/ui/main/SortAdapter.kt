package com.eck.compose_migration.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.eck.compose_migration.databinding.ItemSortBinding
import com.eck.compose_migration.ui.models.SortType

class SortAdapter constructor(
    private var selectedItem: SortType,
    private val onClick: (SortType) -> Unit
) : RecyclerView.Adapter<SortAdapter.ItemViewHolder>() {

    private val items = SortType.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemSortBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        viewHolder.bind(selectedItem, items[position]) {
            selectedItem = it
            onClick(it)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = items.size

    class ItemViewHolder(private val binding: ItemSortBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(selectedItem: SortType, item: SortType, onClick: (SortType) -> Unit) {
            binding.title.text = item.getTitle(binding.root.context)
            binding.check.isVisible = selectedItem == item
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }
}