package com.example.recyclerview

import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class FadeInItemAnimator : DefaultItemAnimator() {
    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.alpha = 0f
        holder.itemView.animate().alpha(1f).setDuration(30000).interpolator = AccelerateDecelerateInterpolator()
        return super.animateAdd(holder)
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.animate().alpha(0f).setDuration(30000).interpolator = AccelerateDecelerateInterpolator()
        return super.animateRemove(holder)
    }
}
