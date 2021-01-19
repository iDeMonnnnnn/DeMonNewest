package com.demon.basemvvm

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.demon.basemvvm.utils.ActivityCallbacks
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
        lateinit var instance: Application
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = this.applicationContext
        val rootDir = MMKV.initialize(this)
        Log.i(TAG, "onCreate:  $rootDir")
        registerActivityLifecycleCallbacks(ActivityCallbacks)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}