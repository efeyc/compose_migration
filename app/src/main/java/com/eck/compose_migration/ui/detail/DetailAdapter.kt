package com.eck.compose_migration.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eck.compose_migration.databinding.ItemDetailBinding

class DetailAdapter constructor(
    private val items: List<Pair<String, String>>
) : RecyclerView.Adapter<DetailAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ItemViewHolder(private val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pair: Pair<String, String>) {
            binding.featureLabel.text = pair.first
            binding.featureValue.text = pair.second
        }
    }
}