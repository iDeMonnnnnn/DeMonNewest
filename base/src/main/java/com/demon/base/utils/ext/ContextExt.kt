package com.demon.base.utils.ext

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

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

/**
 * show的方式展示Fragment
 */
fun FragmentActivity.showFragment(@IdRes id: Int, fragment: Fragment?) {
    fragment ?: return
    tryCatch {
        val transaction = supportFragmentManager.beginTransaction()
        val mFragments = supportFragmentManager.fragments
        if (!mFragments.contains(fragment)) {
            transaction.add(id, fragment)
        }
        mFragments.forEach {
            if (it == fragment) {
                transaction.show(it)
            } else {
                transaction.hide(it)
            }
        }
        transaction.commitAllowingStateLoss()
    }
}
