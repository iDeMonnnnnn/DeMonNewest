package com.demon.base.utils

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Process
import android.telephony.TelephonyManager
import com.demon.base.BaseApp
import com.demon.base.utils.ext.Tag
import com.tencent.mars.xlog.Log


/**
 * @author DeMon
 * Created on 2022/3/29.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
object SystemUtils {

    /**
     * 是否主进程
     */
    fun isMainProcess(context: Context) = getCurrentProcessName(context) == context.packageName

    /**
     * 获取去掉包名后的进程名
     */
    fun getCurrentProcess(context: Context): String {
        val name = getCurrentProcessName(context)?.replace(context.packageName, "")?.replace(":", "")
        return if (name.isNullOrEmpty()) {
            "main"
        } else {
            name
        }
    }

    /**
     * 使用SDK28之后的新增方法[getProcessName()]获取进程名，减少可能存在的应用信息获取违规
     * [https://developer.android.com/reference/android/app/Application.html#getProcessName()]
     */
    fun getCurrentProcessName(context: Context): String? {
        var processName: String? = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            processName = Application.getProcessName()
        } else {
            val pid = Process.myPid()
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            if (manager.runningAppProcesses == null) {
                return null
            }
            for (processInfo in manager.runningAppProcesses) {
                if (processInfo.pid == pid) {
                    processName = processInfo.processName
                    break
                }
            }
        }
        return processName
    }

    /**
     * 获取deviceId,AndroidQ及以上必定为null
     */
    fun getDeviceId(): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android10以上没办法获取getDeviceId了
            // https://developer.android.google.cn/about/versions/10/privacy/changes#non-resettable-device-ids
            return null
        }
        try {
            val telephonyMgr = BaseApp.appContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return telephonyMgr.deviceId
        } catch (exception: Exception) {
            exception.printStackTrace()
            Log.e(Tag, "getImei: " + exception.message)
        }
        return null
    }

}