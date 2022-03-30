package com.demon.demonnewest.module.work

import android.content.Context
import com.tencent.mars.xlog.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

/**
 * @author DeMon
 * Created on 2021/1/18.
 * E-mail 757454343@qq.com
 * Desc:
 */
class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    private val TAG = "UploadWorker"
    override suspend fun doWork(): Result {
        Log.i(TAG, "doWork start: ${System.currentTimeMillis()}")
        for (i in 0 until inputData.getInt("maxValue", 1))
            delay(1000)
        Log.i(TAG, "doWork end: ${System.currentTimeMillis()}")
        return Result.success()
    }
}