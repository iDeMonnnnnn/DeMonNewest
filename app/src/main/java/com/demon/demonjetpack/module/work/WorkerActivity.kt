package com.demon.demonjetpack.module.work

import android.util.Log
import androidx.work.*
import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.base.data.RouterConst
import com.demon.demonjetpack.databinding.ActivityWorkerBinding
import java.util.concurrent.TimeUnit

@Route(path = RouterConst.ACT_WORKER)
class WorkerActivity : MvvmActivity<ActivityWorkerBinding, BaseViewModel>() {


    override fun initData() {
        setToolbar("WorkManager")
        binding.btnOne.setOnClickListener {
            val data = Data.Builder()
                .putInt("maxValue", 5)
                .build()
            val uploadWorkRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
                .setInputData(data)//输入数据
                .setInitialDelay(5, TimeUnit.SECONDS) //延迟5秒
                .addTag("One")
                .build()
            WorkManager.getInstance(this)
                .enqueueUniqueWork("One", ExistingWorkPolicy.REPLACE, uploadWorkRequest)
        }

        binding.btnPeriodic.setOnClickListener {
            val data = Data.Builder()
                .putInt("maxValue", 5)
                .build()
            //可以定义的最短重复间隔是 15 分钟
            //小于这个值，则默认是15分钟
            val uploadWorkRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<UploadWorker>(15, TimeUnit.MINUTES)
                .setInputData(data)
                .addTag("Periodic")
                .build()
            WorkManager.getInstance(this).enqueueUniquePeriodicWork("Periodic", ExistingPeriodicWorkPolicy.KEEP, uploadWorkRequest)
        }

        binding.btnConstraints.setOnClickListener {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED) //wifi
                .setRequiresCharging(true) //充电
                .build()
            val uploadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
                .setConstraints(constraints)
                .addTag("Constraints")
                .build()
            WorkManager.getInstance(this).enqueue(uploadWorkRequest)
        }

        binding.btnRetry.setOnClickListener {
            val retryWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<RetryWorker>()
                .addTag("Retry")
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
            WorkManager.getInstance(this).enqueue(retryWorkRequest)
        }

        binding.btnProgress.setOnClickListener {
            val progressWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<ProgressWorker>()
                .addTag("Progress")
                .build()
            WorkManager.getInstance(this).enqueue(progressWorkRequest)

            WorkManager.getInstance(this).getWorkInfoByIdLiveData(progressWorkRequest.id).observe(this) {
                val progress = it.progress
                val value = progress.getInt("Progress", 0)
                Log.i(TAG, "init Progress: $value")
            }
        }

        binding.btnChaining.setOnClickListener {
            val retryWorkRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<RetryWorker>()
                .addTag("Retry")
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
            val progressWorkRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<ProgressWorker>()
                .addTag("Progress")
                .build()
            val uploadWorkRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
                .addTag("One")
                .build()
            WorkManager.getInstance(this)
                // Candidates to run in parallel
                .beginWith(retryWorkRequest)
                // Dependent work (only runs after all previous work in chain)
                .then(uploadWorkRequest)
                .then(progressWorkRequest)
                // Call enqueue to kick things off
                .enqueue()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        WorkManager.getInstance(this).cancelAllWork()
    }

}