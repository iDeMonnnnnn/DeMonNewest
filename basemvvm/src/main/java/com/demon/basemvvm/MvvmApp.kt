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

    companion object {
        const val TAG = "MvvmApp"
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        val rootDir = MMKV.initialize(this)
        Log.i(TAG, "onCreate:  $rootDir")
        registerActivityLifecycleCallbacks(ActivityCallbacks)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}