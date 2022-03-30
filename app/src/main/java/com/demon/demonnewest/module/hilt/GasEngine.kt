package com.demon.demonnewest.module.hilt

import com.tencent.mars.xlog.Log
import javax.inject.Inject

/**
 * @author DeMon
 * Created on 2022/3/9.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class GasEngine @Inject constructor() : Engine {

    private val TAG = "GasEngine"
    override fun start() {
        Log.i(TAG, "start: ")
    }

    override fun shutdown() {
        Log.i(TAG, "shutdown: ")
    }
}