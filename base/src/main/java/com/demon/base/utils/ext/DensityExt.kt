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
 * 12.dp = 12dp
 */
val Any.dp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.parseFloat(), Resources.getSystem().displayMetrics)

/**
 * 12.sp = 12sp
 */
val Any.sp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.parseFloat(), Resources.getSystem().displayMetrics)

/**
 * 12.intDp = 12dp
 */
val Any.intDp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.parseFloat(), Resources.getSystem().displayMetrics).parseInt()

/**
 * 12.intSp = 12sp
 */
val Any.intSp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.parseFloat(), Resources.getSystem().displayMetrics).parseInt()
