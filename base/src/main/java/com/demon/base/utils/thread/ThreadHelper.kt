package com.demon.base.utils.thread

import android.os.Looper

object ThreadHelper {
    @JvmStatic
    fun runOnMainThread(run: Runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            run.run()
        } else {
            Handlers.sMainHandler.post(run)
        }
    }

    @JvmStatic
    fun runOnMainThread(run: Runnable, delayMillis: Long) {
        Handlers.sMainHandler.postDelayed(run, delayMillis)
    }

    @JvmStatic
    fun runOnIOThread(run: Runnable) {
        if (Looper.myLooper() == Handlers.sIOHandler.looper) {
            run.run()
        } else {
            Handlers.sIOHandler.post(run)
        }
    }

    @JvmStatic
    fun runOnIOThread(run: Runnable, delayMillis: Long) {
        Handlers.sIOHandler.postDelayed(run, delayMillis)
    }

    @JvmStatic
    fun runOnBackgroundThread(run: Runnable) {
        if (Looper.myLooper() == Handlers.sBackgroundHandler.looper) {
            run.run()
        } else {
            Handlers.sBackgroundHandler.post(run)
        }
    }

    @JvmStatic
    fun runOnBackgroundThread(run: Runnable, delayMillis: Long) {
        Handlers.sBackgroundHandler.postDelayed(run, delayMillis)
    }
}