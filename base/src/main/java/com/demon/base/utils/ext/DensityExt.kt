package com.demon.base.utils.ext

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author DeMonnnnnn
 * date 2022/8/5
 * email liu_demon@qq.com
 * desc
 */

/**
 * 18dp
 */
val Int.intDp2Px
    get() = this * Resources.getSystem().displayMetrics.density + 0.5f


val Int.intSp2Px
    get() = this * Resources.getSystem().displayMetrics.scaledDensity + 0.5f

/**
 * 12.dp2px = 12dp
 */
val Any.dp2px
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.parseFloat(), Resources.getSystem().displayMetrics).toInt()

/**
 * 12.sp2px = 12sp
 */
val Any.sp2px
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.parseFloat(), Resources.getSystem().displayMetrics).toInt()
