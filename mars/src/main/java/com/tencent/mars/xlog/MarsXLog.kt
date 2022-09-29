package com.tencent.mars.xlog

import android.content.Context
import com.tencent.mars.BuildConfig

/**
 * @author DeMonnnnnn
 * date 2022/8/30
 * email liu_demon@qq.com
 * desc
 */
object MarsXLog {

    private const val TAG = "MarsXLog"

    /**
     * 初始化Xlog
     *
     * @param context    上下文
     * @param nameprefix 文件前缀
     * @param pubkey     加密key
     */
    @JvmStatic
    @JvmOverloads
    fun initXlog(context: Context, nameprefix: String = "Log", pubkey: String = "") {
        Log.setLogImp(Xlog())

        val cacheDir = if (context.externalCacheDir == null) {
            context.cacheDir.absolutePath + "/xlog"
        } else {
            context.externalCacheDir?.absolutePath + "/xlog"
        }
        val logDir = if (context.getExternalFilesDir(null) == null) {
            context.filesDir.absolutePath + "/xlog"
        } else {
            context.getExternalFilesDir(null)?.absolutePath + "/xlog"
        }
        if (BuildConfig.DEBUG) {
            Xlog.open(true, Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cacheDir, logDir, nameprefix, pubkey)
        } else {
            Xlog.open(true, Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, cacheDir, logDir, nameprefix, pubkey)
        }
        Xlog.setConsoleLogOpen(BuildConfig.DEBUG)
        Log.i(TAG, "initXlog: " + Log.getSysInfo())
    }

    /**
     * 刷新xlog内存，防止丢失
     */
    @JvmStatic
    @JvmOverloads
    fun xlogFlush() {
        Log.appenderFlush(true)
    }

    /**
     * 关闭xlog
     */
    @JvmStatic
    @JvmOverloads
    fun xlogClose() {
        Log.appenderClose()
    }


}