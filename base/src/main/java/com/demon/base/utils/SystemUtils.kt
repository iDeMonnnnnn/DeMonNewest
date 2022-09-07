package com.demon.base.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Context.TELEPHONY_SERVICE
import android.content.pm.ApplicationInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import com.demon.base.BaseApp
import java.io.IOException
import java.io.InputStreamReader
import java.io.LineNumberReader
import java.net.NetworkInterface
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


    private var androidId: String = ""

    /**
     * 获取设备ID
     */
    fun getAndroidId(): String {
        if (TextUtils.isEmpty(androidId)) {
            androidId = Settings.Secure.getString(BaseApp.appContext.contentResolver, Settings.Secure.ANDROID_ID)
        }
        return androidId
    }

    /**
     * 获取设备DeviceId
     */
    fun getDeviceId(): String? {
        var deviceId: String? = null
        try {
            deviceId = (BaseApp.appContext.getSystemService(TELEPHONY_SERVICE) as TelephonyManager).deviceId
            //华为MatePad上，神奇的获得unknown，特此修复
            if (TextUtils.isEmpty(deviceId) || "unknown" == deviceId) {
                return null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return deviceId
    }


    /**
     * 获取mac地址（适配所有Android版本）
     */
    fun getMac(): String? {
        var mac: String? = null
        mac = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            getMacWifi(BaseApp.appContext)
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            getMacAddress()
        } else {
            getMacFromHardware()
        }
        return mac
    }


    /**
     * Android 6.0 之前（不包括6.0）获取mac地址
     * 必须的权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     *
     * @param context * @return
     */
    @SuppressLint("HardwareIds")
    private fun getMacWifi(context: Context): String? {
        var mac: String? = null
        try {
            val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            var info = wifi.connectionInfo
            mac = info.macAddress
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mac
    }

    /**
     * Android 6.0-Android 7.0 获取mac地址
     */
    private fun getMacAddress(): String? {
        var macSerial: String? = null
        var str = ""
        try {
            val pp = Runtime.getRuntime().exec("cat/sys/class/net/wlan0/address")
            val ir = InputStreamReader(pp.inputStream)
            val input = LineNumberReader(ir)
            while (true) {
                str = input.readLine()
                if (str != null) {
                    macSerial = str.trim { it <= ' ' } //去空格
                    break
                }
            }
        } catch (ex: IOException) {
            // 赋予默认值
            ex.printStackTrace()
        }
        return macSerial
    }

    /**
     * Android 7.0之后获取Mac地址
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET"></uses-permission>
     */
    private fun getMacFromHardware(): String? {
        try {
            val all: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (info in all) {
                if (!info.name.equals("wlan0", true)) {
                    continue
                }
                val data: ByteArray = info.hardwareAddress ?: return null
                val res1 = StringBuilder()
                for (b in data) {
                    res1.append(String.format("%02X:", b))
                }
                if (res1.isNotEmpty()) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return null
    }
}