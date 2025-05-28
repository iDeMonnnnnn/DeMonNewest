package com.demon.base.utils.ext

import androidx.core.text.isDigitsOnly
import com.hjq.toast.Toaster
import kotlinx.coroutines.launch

/**
 * @author DeMon
 * Created on 2022/3/28.
 * E-mail idemon_liu@qq.com
 * Desc: Toast相关拓展
 */
fun String.toast() {
    scopeMain.launch {
        Toaster.show(this)
    }
}

fun Int.toast() {
    scopeMain.launch {
        Toaster.show(this)
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