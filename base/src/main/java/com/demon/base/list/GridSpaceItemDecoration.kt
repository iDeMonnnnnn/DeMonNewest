package com.demon.base.list

import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.util.Log

/**
 * @author DeMon
 * Created on 2022/4/16.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class GridSpaceItemDecoration(//横条目数量
        private val mSpanCount: Int, //行间距
        private val mRowSpacing: Int, // 列间距
        private val mColumnSpacing: Int) : ItemDecoration() {

    private val TAG = "GridSpaceItemDecoration"

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // 获取view 在adapter中的位置。
        val column = position % mSpanCount // view 所在的列
        outRect.left = column * mColumnSpacing / mSpanCount // column * (列间距 * (1f / 列数))
        outRect.right = mColumnSpacing - (column + 1) * mColumnSpacing / mSpanCount // 列间距 - (column + 1) * (列间距 * (1f /列数))
/*        Log.e(TAG, "position:" + position
                + "    columnIndex: " + column
                + "    left,right ->" + outRect.left + "," + outRect.right)*/
        // 如果position > 行数，说明不是在第一行，则不指定行高，其他行的上间距为 top=mRowSpacing
        if (position >= mSpanCount) {
            outRect.top = mRowSpacing // item top
        }
    }
}