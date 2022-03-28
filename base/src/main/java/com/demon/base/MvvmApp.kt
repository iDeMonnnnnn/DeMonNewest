package com.demon.base

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.demon.qfsolution.QFHelper
import com.hjq.toast.ToastUtils
import com.hjq.toast.style.ToastAliPayStyle
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
        val rootDir = MMKV.initialize(this)
        Log.i(TAG, "onCreate:  $rootDir")
        QFHelper.init(this, "fileProvider")
        ToastUtils.init(this, ToastAliPayStyle(this))
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}