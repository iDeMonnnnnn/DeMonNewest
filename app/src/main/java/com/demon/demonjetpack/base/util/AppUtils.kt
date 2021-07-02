package com.demon.demonjetpack.base.util

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Process
import java.io.IOException
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

/**
 * @author DeMon
 * Created on 2021/6/29.
 * E-mail 757454343@qq.com
 * Desc:
 */
object AppUtils {
    /**
     * 获取当前进程名
     */
    fun getCurrentProcessName(context: Context): String? {
        var processName = ""
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
        return processName
    }

    /**
     * https://tech.meituan.com/2014/06/13/mt-apk-packaging.html
     * 美团渠道标识方案，获取渠道号
     */
    fun getChannel(context: Context, channelKey: String): String {
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
        return if (split.size >= 2) {
            ret.substring(split[0].length + 1)
        } else {
            "default"
        }
    }
}