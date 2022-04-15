package com.demon.base.utils.callback

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.demon.base.utils.ext.Tag
import com.demon.base.utils.helper.ActivityHelper
import com.tencent.mars.xlog.Log

/**
 * @author DeMon
 * Created on 2022/3/29.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
object ActivityCallback : Application.ActivityLifecycleCallbacks {

    var isBackground: Boolean = false
        get() = foregroundCount <= 0

    private var foregroundCount = 0

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        ActivityHelper.pushActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        foregroundCount++
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        foregroundCount--
        if (isBackground) {
            Log.appenderFlush(true)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        ActivityHelper.popActivity(activity)
    }
}