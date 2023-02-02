package com.ssafy.campinity.common.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearItemDecoration(
    private val context: Context, private val orientation: Int, private val offsetDp: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        if (position != 0) when (orientation) {
            RecyclerView.VERTICAL -> outRect.top = offsetDp.px(context)
            RecyclerView.HORIZONTAL -> outRect.left = offsetDp.px(context)
        }
    }
}

class GridItemDecoration(
    private val context: Context,
    private val span: Int,
    private val offsetVerticalDp: Int,
    private val offsetHorizontalDp: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        if (position > span - 1) outRect.top = offsetVerticalDp.px(context)
        if (position % span != 0) outRect.left = offsetHorizontalDp.px(context)
    }
}