package com.example.recyclerview

import androidx.recyclerview.widget.DiffUtil

data class Item(
    val id: Int,
    val title: String,
    val description: String
)

class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}
