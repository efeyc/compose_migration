package com.eck.compose_migration.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eck.compose_migration.databinding.ItemAdvertBinding
import com.eck.compose_migration.ui.models.UiAdvert

class MainAdapter constructor(
    private val onClick: (UiAdvert) -> Unit
) : RecyclerView.Adapter<MainAdapter.ItemViewHolder>() {

    private var items: List<UiAdvert> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<UiAdvert>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemAdvertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        viewHolder.bind(items[position], onClick)
    }

    override fun getItemCount() = items.size

    class ItemViewHolder(private val binding: ItemAdvertBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UiAdvert, onClick: (UiAdvert) -> Unit) {
            val context = binding.root.context
            binding.title.text = item.description
            binding.address.text = item.city ?: ""
            binding.price.text = "${item.price} €"
            binding.locationIcon.isVisible = item.city != null
            item.images.firstOrNull()?.let { url ->
                Glide.with(context)
                    .load(url)
                    .into(binding.image)
            }
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }
}