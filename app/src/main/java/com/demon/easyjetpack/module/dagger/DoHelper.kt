package com.demon.easyjetpack.module.dagger

import com.demon.easyjetpack.bean.Info
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
class DoHelper
@Inject
constructor() {


    var name: String = "Zhangsan"


    fun getNowTime(): Long {
        return System.currentTimeMillis()
    }

    @Inject
    protected lateinit var haHelper: HaHelper


    fun getHa() = haHelper.getAddress()


    @Inject
    protected lateinit var gson: Gson


    fun getGson() = gson.toJson(Info())
}