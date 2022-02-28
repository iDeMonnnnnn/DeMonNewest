package com.demon.basemvvm.utils

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


/**
 * @author DeMon
 * Created on 2020/11/25.
 * E-mail 757454343@qq.com
 * Desc:
 */

/**
 * 获取当前类的TAG
 */
inline val <T : Any> T.Tag: String
    get() = this.javaClass.simpleName


fun Context.dpToPx(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f * if (dp >= 0) 1 else -1).toInt()
}

fun Context.getCompatColor(@ColorRes id: Int) = ContextCompat.getColor(this, id)