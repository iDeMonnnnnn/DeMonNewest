package com.demon.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.startup.AppInitializer
import com.demon.base.startup.ARouterInitializer
import com.demon.base.utils.SystemUtils
import com.demon.base.utils.helper.ActivityHelper
import com.demon.qfsolution.QFHelper
import com.hjq.toast.ToastUtils
import com.hjq.toast.style.BlackToastStyle
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.MarsXLog
import com.tencent.mmkv.MMKV


/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
open class BaseApp : Application() {
    protected val TAG = this.javaClass.simpleName

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext
        /**
         * App Startup在manifest配置只会在主进程中执行，
         * 因此非主进程自己主动调用执行
         */
        if (!SystemUtils.isMainProcess(applicationContext)) {
            AppInitializer.getInstance(this).initializeComponent(ARouterInitializer::class.java)
        }
        val rootDir = MMKV.initialize(this)
        Log.i(TAG, "onCreate:  $rootDir")
        QFHelper.init(this, "fileProvider")
        ToastUtils.init(this, BlackToastStyle())
        registerActivityLifecycleCallbacks(ActivityHelper.ActivityCallback)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }


    override fun onLowMemory() {
        super.onLowMemory()
        Log.i(TAG, "onLowMemory: ")
        MarsXLog.xlogFlush()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.i(TAG, "onTrimMemory: $level")
        MarsXLog.xlogFlush()
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.i(TAG, "onTerminate: ")
        MarsXLog.xlogClose()
    }

}