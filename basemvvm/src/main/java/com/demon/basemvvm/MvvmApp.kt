package com.demon.basemvvm

import android.app.Application
import android.content.Context
import com.demon.basemvvm.utils.ActivityCallbacks

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
open class MvvmApp : Application() {

    companion object {
        private lateinit var instance: Application

        fun getApplication() = instance

        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(ActivityCallbacks)
    }

}