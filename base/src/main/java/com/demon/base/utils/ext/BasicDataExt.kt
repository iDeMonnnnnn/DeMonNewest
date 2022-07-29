package com.demon.base.utils.ext

import android.content.res.Resources
import android.text.TextUtils
import android.util.TypedValue

/**
 * @author DeMon
 * Created on 2022/3/28.
 * E-mail idemon_liu@qq.com
 * Desc: 基础数据类型相关扩展
 */
//--Any--//
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


//--String--//
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
 * 转成Int
 */
fun Any?.parseInt(defaultValue: Int = 0): Int {
    this ?: return defaultValue
    return tryCatch(defaultValue) {
        if (this is Number) {
            this.toInt()
        } else {
            "$this".toInt()
        }
    }
}

/**
 * 转成Double
 */
fun Any?.parseDouble(defaultValue: Double = 0.0): Double {
    this ?: return defaultValue
    return tryCatch(defaultValue) {
        if (this is Number) {
            this.toDouble()
        } else {
            "$this".toDouble()
        }
    }
}

/**
 * 转成Long
 */
fun Any?.parseLong(defaultValue: Long = 0): Long {
    this ?: return defaultValue
    return tryCatch(defaultValue) {
        if (this is Number) {
            this.toLong()
        } else {
            "$this".toLong()
        }
    }
}

/**
 * 转成Float
 */
fun Any?.parseFloat(defaultValue: Float = 0f): Float {
    this ?: return defaultValue
    return tryCatch(defaultValue) {
        if (this is Number) {
            this.toFloat()
        } else {
            "$this".toFloat()
        }
    }
}

/**
 * 大于0的时候返回true，其他返回false
 */
fun Any?.toBool(defaultValue: Boolean = false): Boolean {
    this ?: return defaultValue
    return tryCatch(defaultValue) {
        this.parseInt() > 0
    }
}


//--Boolean--//

fun <T> Boolean.whatIf(ifTrue: T, other: T): T {
    return if (this) {
        ifTrue
    } else {
        other
    }
}

fun <T> Boolean.whatIf(ifBlock: () -> T, elseBlock: () -> T): T {
    return if (this) {
        ifBlock()
    } else {
        elseBlock()
    }
}