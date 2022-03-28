package com.demon.base.utils

import android.util.Log

/**
 * @author DeMon
 * Created on 2022/3/28.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
inline fun tryCatch(block: () -> Unit): Unit = try {
    block()
} catch (e: Exception) {
    e.printStackTrace()
    Log.e("TryExt", "tryCatch: " + e.printStackTrace())
    Unit
}

/**
 * 当需要返回值try的时候使用
 */
inline fun <T> tryCatch(default: T, block: () -> T): T {
    return try {
        block()
    } catch (e: Exception) {
        Log.e("TryExt", "tryCatch: " + e.printStackTrace())
        default
    }
}

/**
 * 通用输出异常的方法
 */
inline fun Throwable?.errorLog() {
    Log.e("TryExt", Log.getStackTraceString(this))
}