package com.demon.easyjetpack.base.ext

import android.app.ActivityManager
import android.content.Context
import android.os.Process
import android.widget.Toast
import androidx.core.text.isDigitsOnly

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */


fun String.toast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.toastEmpty(context: Context): Boolean {
    return if (this.isNullOrEmpty()) {
        Toast.makeText(context, "不能为空！", Toast.LENGTH_SHORT).show()
        true
    } else {
        false
    }
}


fun String.toastDigital(context: Context): Boolean {
    return if (this.isNullOrEmpty() || !this.isDigitsOnly()) {
        Toast.makeText(context, "请输入数字！", Toast.LENGTH_SHORT).show()
        true
    } else {
        false
    }
}

fun Context.getCurrentProcessName(): String? {
    var processName = ""
    val pid = Process.myPid()
    val manager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager ?: return null
    if (manager.runningAppProcesses == null) {
        return null
    }
    for (processInfo in manager.runningAppProcesses) {
        if (processInfo.pid == pid) {
            processName = processInfo.processName
            break
        }
    }
    return processName
}



