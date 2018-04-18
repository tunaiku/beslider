package app.beslider.com.helper

import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView

class SnapHelper(var onSelectedItemChange: OnSelectedItemChange) : PagerSnapHelper() {

    var lastPosition = 0

    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager?, velocityX: Int, velocityY: Int): Int {
        val position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
        if(position != RecyclerView.NO_POSITION && lastPosition != position && position < layoutManager!!.itemCount) {
            onSelectedItemChange.onSelectedItemChange(position)
            lastPosition = position
        }

        return position
    }

    interface OnSelectedItemChange {
        fun onSelectedItemChange(position: Int)
    }
}