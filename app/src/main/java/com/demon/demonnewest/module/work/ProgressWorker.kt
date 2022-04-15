package com.demon.demonnewest.module.work

import android.content.Context
import com.tencent.mars.xlog.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

/**
 * @author DeMon
 * Created on 2021/1/18.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class ProgressWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    companion object {
        private const val TAG = "ProgressWorker"
        const val Progress = "Progress"
    }

    override suspend fun doWork(): Result {
        for (i in 0 until 6) {
            setProgress(workDataOf(Progress to 20 * i))
            Log.i(TAG, "doWork: ${System.currentTimeMillis()}")
            delay(1000)
        }
        return Result.success()
    }
}