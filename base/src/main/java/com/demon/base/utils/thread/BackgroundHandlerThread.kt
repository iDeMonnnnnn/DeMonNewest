package com.demon.base.utils.thread

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import com.demon.base.utils.thread.BackgroundHandlerThread

class BackgroundHandlerThread private constructor() {

    /**
     * 使用内部类，解决线程安全问题
     * @author moo
     */
    private object Holder {
        val instance = BackgroundHandlerThread()

        init {
            instance.init()
        }
    }

    private lateinit var mHandlerThread: HandlerThread

    lateinit var handler: Handler

    private fun init() {
        mHandlerThread = HandlerThread(BackgroundHandlerThread::class.java.simpleName)
        mHandlerThread.start()
        handler = object : Handler(mHandlerThread.looper) {
            override fun handleMessage(msg: Message) {}
        }
    }

    companion object {
        @JvmStatic
        fun getInstance() = Holder.instance
    }
}