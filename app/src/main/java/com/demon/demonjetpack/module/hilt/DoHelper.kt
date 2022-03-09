package com.demon.demonjetpack.module.hilt

import com.demon.demonjetpack.bean.Info
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

    fun getNowTime(): Long {
        return System.currentTimeMillis()
    }


    @Inject
    protected lateinit var gson: Gson


    fun getGson() = gson.toJson(Info())
}