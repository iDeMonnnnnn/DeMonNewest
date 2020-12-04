package com.demon.basemvvm

import android.app.Application
import com.demon.basemvvm.utils.ActivityCallbacks

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
open class MvvmApp : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityCallbacks)
    }

}