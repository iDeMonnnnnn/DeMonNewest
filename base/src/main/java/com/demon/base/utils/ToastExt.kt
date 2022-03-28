package com.demon.base.utils

import androidx.core.text.isDigitsOnly
import com.demon.base.utils.thread.ThreadHelper
import com.hjq.toast.ToastUtils

/**
 * @author DeMon
 * Created on 2022/3/28.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
fun String.toast() {
    ThreadHelper.runOnMainThread {
        ToastUtils.show(this)
    }
}

fun Int.toast() {
    ThreadHelper.runOnMainThread {
        ToastUtils.show(this)
    }
}

fun String?.toastEmpty(): Boolean {
    return this.isNullOrEmpty().whatIf({
        "不能为空！".toast()
        true
    }, {
        false
    })
}


fun String?.toastDigital(): Boolean {
    return (this.isNullOrEmpty() || !this.isDigitsOnly()).whatIf({
        "请输入数字！".toast()
        true
    }, {
        false
    })
}