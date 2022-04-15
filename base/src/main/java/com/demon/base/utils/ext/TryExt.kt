package com.demon.base.utils.ext

import com.tencent.mars.xlog.Log

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
    Log.e("TryExt", "tryCatch: ", e)
    Unit
}

/**
 * 当需要返回值try的时候使用
 */
inline fun <T> tryCatch(default: T, block: () -> T): T {
    return try {
        block()
    } catch (e: Exception) {
        Log.e("TryExt", "tryCatch: ", e)
        default
    }
}

/**
 * 通用输出异常的方法
 */
fun Throwable?.errorLog() {
    Log.e("TryExt", "errorLog: ", this)
}

/**
 * 获取异常堆栈信息
 */
fun Throwable?.getStackTraceString() = android.util.Log.getStackTraceString(this)
