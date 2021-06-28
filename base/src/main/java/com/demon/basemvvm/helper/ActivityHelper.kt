package com.demon.basemvvm.helper

import android.app.Activity
import java.util.*


/**
 * @author DeMon
 * Created on 2020/4/10.
 * E-mail 757454343@qq.com
 * Desc:
 */
object ActivityHelper {
    private var sActivities: MutableList<Activity> = Collections.synchronizedList(LinkedList<Activity>())

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
    fun getCurrentActivity(): Activity? {
        return if (sActivities.isEmpty()) {
            null
        } else {
            sActivities[sActivities.size - 1]
        }
    }

    /**
     * 关闭当前的Activity
     */
    fun finishCurrentActivity() {
        val activity = getCurrentActivity()
        activity?.finish()
    }

    /**
     * 关闭某个Activity及除当前Activity的其他Activity
     *
     * @param target          目标Activity.class
     * @param currentActivity 当前Activity的Activity
     */
    fun finishActivitys(target: Class<out Activity?>, currentActivity: Activity) {
        if (sActivities.isEmpty()) {
            return
        }
        for (i in sActivities.indices) {
            val activity = sActivities[i]
            // 将除了当前Activity和MainActivity的其他Activity关闭
            if (activity.javaClass.name != target.name && activity !== currentActivity) {
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
        for (i in sActivities.indices) {
            val activity = sActivities[i]
            if (activity.javaClass.name == target.name) {
                activity.finish() //finish方法会调用popActivity()方法移出栈
            }
        }
    }

    //退出所有activity
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
}