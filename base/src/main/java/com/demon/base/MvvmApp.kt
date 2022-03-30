package com.demon.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.startup.AppInitializer
import com.demon.base.startup.ARouterInitializer
import com.demon.base.utils.SystemUtils
import com.demon.base.utils.callback.ActivityCallback
import com.demon.qfsolution.QFHelper
import com.hjq.toast.ToastUtils
import com.hjq.toast.style.ToastAliPayStyle
import com.tencent.mars.xlog.Log
import com.tencent.mmkv.MMKV


/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
open class MvvmApp : Application() {
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
        ToastUtils.init(this, ToastAliPayStyle(this))
        registerActivityLifecycleCallbacks(ActivityCallback)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }


    override fun onLowMemory() {
        super.onLowMemory()
        Log.i(TAG, "onLowMemory: ")
        Log.appenderFlush(true)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.i(TAG, "onTrimMemory: $level")
        Log.appenderFlush(true)
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.i(TAG, "onTerminate: ")
        Log.appenderClose()
    }

}