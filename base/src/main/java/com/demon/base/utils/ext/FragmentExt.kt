package com.demon.base.utils.ext

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * @author DeMonnnnnn
 * date 2022/8/5
 * email liu_demon@qq.com
 * desc
 */
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


/**
 * show的方式展示Fragment
 */
fun Fragment.showFragment(@IdRes id: Int, fragment: Fragment?) {
    fragment ?: return
    tryCatch {
        val transaction = childFragmentManager.beginTransaction()
        val mFragments = childFragmentManager.fragments
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

/**
 * replace的方式展示Fragment
 */
fun FragmentActivity.replaceFragment(@IdRes id: Int, fragment: Fragment?) {
    fragment ?: return
    tryCatch {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(id, fragment).commitAllowingStateLoss()
    }
}

/**
 * replace的方式展示Fragment
 */
fun Fragment.replaceFragment(@IdRes id: Int, fragment: Fragment?) {
    fragment ?: return
    tryCatch {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(id, fragment).commitAllowingStateLoss()
    }
}