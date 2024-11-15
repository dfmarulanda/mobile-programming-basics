package com.example.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

    fun bind(item: Item) {
        titleTextView.text = item.title
        descriptionTextView.text = item.description
    }
}