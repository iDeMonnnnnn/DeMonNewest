package com.demon.base.utils

import android.content.res.Resources
import android.text.TextUtils
import android.util.TypedValue

/**
 * @author DeMon
 * Created on 2022/3/28.
 * E-mail idemon_liu@qq.com
 * Desc: 基础数据类型相关扩展
 */
/**
 * 12.dp2px = 12dp
 */
val Any.dp2px
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, "$this".parseFloat(), Resources.getSystem().displayMetrics).toInt()
/**
 * 12.sp2px = 12sp
 */
val Any.sp2px
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, "$this".parseFloat(), Resources.getSystem().displayMetrics).toInt()

/**
 * 如果为空或者空字符串则返回默认值。
 * val test = ""; val test2 = test nullDefault "123"
 *
 * @param defaultValue 默认值
 */
infix fun String?.useDefault(defaultValue: String): String =
    if (this.isNullOrEmpty()) defaultValue else this

/**
 * 判断字符串是否为1
 */
fun String?.oneIsTrue(): Boolean =
    !this.isNullOrEmpty() && "1" == this


/**
 * 判断字符串是否为0
 */
fun String?.zeroIsTrue(): Boolean =
    !this.isNullOrEmpty() && "0" == this

/**
 * 字符串为"0"或者空
 */
fun String?.isZeroOrEmpty() = this.isNullOrEmpty() || "0" == this

/**
 * String转成Int
 */
fun String?.parseInt(defaultValue: Int = 0): Int {
    if (this.isNullOrEmpty()) return defaultValue
    return tryCatch(defaultValue) {
        this.toInt()
    }
}

/**
 * String转成Double
 */
fun String?.parseDouble(defaultValue: Double = 0.0): Double {
    if (this.isNullOrEmpty()) return defaultValue
    return tryCatch(defaultValue) {
        this.toDouble()
    }
}

/**
 * String转成Float
 */
fun String?.parseFloat(defaultValue: Float = 0f): Float {
    if (this.isNullOrEmpty()) return defaultValue
    return tryCatch(defaultValue) {
        this.toFloat()
    }
}

/**
 * 大于0的时候返回true，其他返回false
 */
fun String?.toBool(): Boolean {
    if (TextUtils.isEmpty(this))
        return false
    return try {
        Integer.parseInt(this ?: "") > 0
    } catch (n: NumberFormatException) {
        n.errorLog()
        false
    }
}

/**
 * String转成Long
 */
fun String?.parseLong(defaultValue: Long = 0): Long {
    if (this.isNullOrEmpty()) return defaultValue
    return tryCatch(defaultValue) {
        this.toLong()
    }
}

/**
 * 浮点数相加，默认取小数点后两位，四舍五入
 * @param count 小数点后几位
 */
fun Any.add(x: Any, count: Int = 2) = tryCatch("0") {
    this.toString().toBigDecimal().add(x.toString().toBigDecimal()).toString().getPointNums(count)
}

/**
 * 浮点数相减，默认取小数点后两位，四舍五入
 * @param count 小数点后几位
 */
fun Any.subtract(x: Any, count: Int = 2) = tryCatch("0") {
    this.toString().toBigDecimal().subtract(x.toString().toBigDecimal()).toString().getPointNums(count)
}

/**
 * 浮点数相乘，默认取小数点后两位，四舍五入
 * @param count 小数点后几位
 */
fun Any.multiply(x: Any, count: Int = 2) = tryCatch("0") {
    this.toString().toBigDecimal().multiply(x.toString().toBigDecimal()).toString().getPointNums(count)
}


/**
 * 浮点数相除，默认取小数点后两位，四舍五入
 * @param count 小数点后几位
 */
fun Any.divide(x: Any, count: Int = 2) = tryCatch("0") {
    this.toString().toBigDecimal().divide(x.toString().toBigDecimal()).toString().getPointNums(count)
}

/**
 * 强制获取小数点后的n位
 */
fun Any?.getPointNums(count: Int = 2) = tryCatch("0") {
    this ?: return "0";
    return "%.${count}f".format(this.toString().parseFloat())
}