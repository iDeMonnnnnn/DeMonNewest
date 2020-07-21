package com.demon.basemvvm.helper

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * @author DeMon
 * Created on 2020/7/20.
 * E-mail 757454343@qq.com
 * Desc:
 */
object ActivityCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStarted(p0: Activity) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        ActivityHelper.popActivity(activity)
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        ActivityHelper.pushActivity(activity)
    }

    override fun onActivityResumed(p0: Activity) {
    }
}