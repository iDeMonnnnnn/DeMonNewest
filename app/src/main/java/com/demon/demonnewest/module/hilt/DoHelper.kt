package com.demon.demonnewest.module.hilt

import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author DeMon
 * Created on 2020/1/14.
 * E-mail 757454343@qq.com
 * Desc:
 */
@Singleton
class DoHelper @Inject constructor() {
    private val TAG = "DoHelper"

    fun getNowTime(): Long {
        return System.currentTimeMillis()
    }


    @Inject
    protected lateinit var gson: Gson


    fun getGson(): String {
        val map = mutableMapOf<String, Any>(
            "name" to TAG, "time" to System.currentTimeMillis()
        )
        return gson.toJson(map)
    }
}