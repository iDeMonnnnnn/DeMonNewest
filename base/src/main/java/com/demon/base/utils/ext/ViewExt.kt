package com.demon.base.utils.ext

import android.view.View
import android.view.ViewGroup

/**
 * @author DeMonnnnnn
 * date 2022/8/5
 * email liu_demon@qq.com
 * desc
 */
val View?.toVisible: Unit
    get() {
        this?.visibility = View.VISIBLE
    }

/**
 * 设置View的显隐为GONE
 */
val View?.toGone: Unit
    get() {
        this?.visibility = View.GONE
    }

/**
 * 设置View的显隐为INVISIBLE
 */
val View?.toInvisible: Unit
    get() {
        this?.visibility = View.INVISIBLE
    }


fun View.visibleOrGone(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}


fun View.visibilityOrIn(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}


/**
 * 增加View的长宽，margin
 */
fun View?.addLayoutParams(width: Int = 0, height: Int = 0, marginStart: Int = 0, marginEnd: Int = 0, topMargin: Int = 0, bottomMargin: Int = 0) {
    this ?: return
    var lp: ViewGroup.LayoutParams? = this.layoutParams
    if (lp == null) {
        lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    val layoutParams = lp as ViewGroup.MarginLayoutParams
    layoutParams.marginStart += marginStart
    layoutParams.marginEnd += marginEnd
    layoutParams.topMargin += topMargin
    layoutParams.bottomMargin += bottomMargin
    layoutParams.width += width
    layoutParams.height += height
    this.layoutParams = layoutParams
}

/**
 * 设置View的长宽，margin
 */
fun View?.setLayoutParams(width: Int = -1, height: Int = -1, marginStart: Int = -1, marginEnd: Int = -1, topMargin: Int = -1, bottomMargin: Int = -1) {
    this ?: return
    var lp: ViewGroup.LayoutParams? = this.layoutParams
    if (lp == null) {
        lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    val layoutParams = lp as ViewGroup.MarginLayoutParams
    if (marginStart != -1)
        layoutParams.marginStart = marginStart
    if (marginEnd != -1)
        layoutParams.marginEnd = marginEnd
    if (topMargin != -1)
        layoutParams.topMargin = topMargin
    if (bottomMargin != -1)
        layoutParams.bottomMargin = bottomMargin
    if (width != -1)
        layoutParams.width = width
    if (height != -1)
        layoutParams.height = height
    this.layoutParams = layoutParams
}