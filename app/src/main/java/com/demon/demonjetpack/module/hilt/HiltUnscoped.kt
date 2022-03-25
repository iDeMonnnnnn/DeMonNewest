package com.demon.demonjetpack.module.hilt

import com.demon.base.MvvmApp
import com.demon.demonjetpack.base.hilt.UnscopedEntryPoint
import com.google.gson.Gson
import dagger.hilt.android.EntryPointAccessors

/**
 * @author DeMon
 * Created on 2022/3/9.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class HiltUnscoped {
    private val TAG = "HiltUnscoped"
    val gson: Gson by lazy {
        EntryPointAccessors.fromApplication(MvvmApp.appContext, UnscopedEntryPoint::class.java).provideGson()
    }


    fun getGson(): String {
        val map = mutableMapOf<String, Any>(
            "name" to TAG, "time" to System.currentTimeMillis()
        )
        return gson.toJson(map)
    }
}