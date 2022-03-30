package com.demon.base.utils.ext

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * @author DeMon
 * Created on 2022/3/28.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
fun Context.dpToPx(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f * if (dp >= 0) 1 else -1).toInt()
}

fun Context.getCompatColor(@ColorRes id: Int) = ContextCompat.getColor(this, id)

/**
 * 检测多个权限是否已经全部获取
 */
fun Context.checkPermission(vararg permissions: String): Boolean {
    var flag = false
    permissions.forEach {
        flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
    return flag
}