package com.demon.base.utils.ext

import android.view.View

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