package com.demon.base.utils.helper

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*
import kotlin.system.exitProcess


/**
 * @author DeMon
 * Created on 2020/4/10.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
object ActivityHelper {
    var sActivities: MutableList<Activity> = Collections.synchronizedList(LinkedList<Activity>())

    /**
     * 当前是否只有一个Activity
     */
    fun isSingle() = sActivities.size == 1

    /**
     * 将Activity添加到栈中
     * 应避免直接调用此方法，在Activity生命周期的onCreate()方法中会调用此方法
     *
     * @param activity 要添加的Activity
     */
    fun pushActivity(activity: Activity) {
        sActivities.add(activity)
    }

    /**
     * 移除一个Activity
     * 应避免直接调用此方法，关闭Activity应调用Activity的finish()
     * 在Activity生命周期的onDestroy()方法中会调用此方法
     *
     * @param activity 要移除的Activity
     */
    fun popActivity(activity: Activity?) {
        if (sActivities.isNotEmpty() && activity != null) {
            sActivities.remove(activity)
        }
    }

    /**
     * 获取当前的Activity
     *
     * @return 当前的Activity
     */
    fun getTopActivity(): Activity? {
        return if (sActivities.isEmpty()) {
            null
        } else {
            sActivities[sActivities.size - 1]
        }
    }

    /**
     * 关闭当前的Activity
     */
    fun finishTopActivity() {
        val activity = getTopActivity()
        activity?.finish()
    }

    /**
     * 关闭除targets的其他Activity
     *
     * @param target          目标Activity.class
     */
    fun finishActivitys(vararg targets: Class<out Activity?>) {
        if (sActivities.isEmpty()) {
            return
        }
        val targetList: List<String> = targets.map { it.name }
        for (activity in sActivities) {
            // 将除了targets的其他Activity关闭
            if (!targetList.contains(activity.javaClass.name)) {
                activity.finish() // finish方法会调用popActivity()方法移出栈
            }
        }
    }

    /**
     * 关闭某个非单例多次打开的activity
     */
    fun finishAnActivity(target: Class<out Activity?>) {
        if (sActivities.isEmpty()) {
            return
        }
        for (activity in sActivities) {
            if (activity.javaClass.name == target.name) {
                activity.finish() //finish方法会调用popActivity()方法移出栈
            }
        }
    }

    /**
     * 退出所有activity
     */
    fun finishAllActivity() {
        for (activity in sActivities) {
            activity.finish()
        }
    }

    /**
     * 退出应用程序
     */
    fun appExit() {
        finishAllActivity()
        exitProcess(0)
    }

    /**
     * 当前activity是否在前台显示
     *
     * @param currentActivity
     * @return
     */
    fun isTopActivity(currentActivity: Activity): Boolean {
        return currentActivity === sActivities[sActivities.size - 1]
    }

    /**
     * 根据Activity类获取栈内的Activity对象
     */
    @JvmStatic
    fun getActivity(target: Class<out Activity?>): Activity? {
        if (sActivities.isEmpty()) {
            return null
        }
        var activity: Activity? = null
        for (a in sActivities) {
            if (a.javaClass.name == target.name) {
                activity = a
            }
        }
        return activity
    }


    val isBackground: Boolean
        get() = ActivityCallback.foregroundCount <= 0

    object ActivityCallback : Application.ActivityLifecycleCallbacks {

        var foregroundCount = 0

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            pushActivity(activity)
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
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            popActivity(activity)
        }
    }
}