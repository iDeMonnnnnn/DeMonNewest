package com.demon.base.utils

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import android.os.Process
import java.io.IOException
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

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
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager ?: return null
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

    private var apkChannel = ""

    /**
     * https://tech.meituan.com/2014/06/13/mt-apk-packaging.html
     * 美团渠道标识方案，获取渠道号
     */
    fun getChannel(context: Context, channelKey: String = "apkchannel"): String {
        if (apkChannel.isNotEmpty()) return apkChannel
        val appinfo: ApplicationInfo = context.applicationInfo
        val sourceDir = appinfo.sourceDir
        //默认放在meta-inf/里， 所以需要再拼接一下
        val key = "META-INF/$channelKey"
        var ret = ""
        var zipfile: ZipFile? = null
        try {
            zipfile = ZipFile(sourceDir)
            val entries: Enumeration<*> = zipfile.entries()
            while (entries.hasMoreElements()) {
                val entry: ZipEntry = entries.nextElement() as ZipEntry
                val entryName: String = entry.name
                if (entryName.startsWith(key)) {
                    ret = entryName
                    break
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        val split = ret.split("_").toTypedArray()
        apkChannel = if (split.size >= 2) {
            ret.substring(split[0].length + 1)
        } else {
            "default"
        }
        return apkChannel
    }
}