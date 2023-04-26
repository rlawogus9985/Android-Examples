package com.example.globalaos

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemPhotoDecoration(private val totalspace: Int, private val spanCount: Int, private val itemWidth: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
//        val position = parent.getChildAdapterPosition(view)

        val margin = (totalspace - itemWidth * spanCount) / (spanCount * 2)

        val topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, view.resources.displayMetrics).toInt()
        outRect.set(margin,topMargin,margin,topMargin)
    }

}