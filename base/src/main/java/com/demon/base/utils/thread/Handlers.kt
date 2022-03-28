package com.demon.base.utils.thread

import android.os.Handler
import android.os.Looper


object Handlers {
    @JvmField
    val sMainHandler = Handler(Looper.getMainLooper())
    @JvmField
    val sIOHandler = IOHandlerThread.getInstance().handler
    @JvmField
    val sBackgroundHandler = BackgroundHandlerThread.getInstance().handler
}