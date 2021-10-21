package com.demon.demonjetpack.init

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import com.demon.basemvvm.utils.Tag

/**
 * @author DeMon
 * Created on 2021/1/19.
 * E-mail 757454343@qq.com
 * Desc:
 */
class WorkManagerInitializer : Initializer<WorkManager> {
    override fun create(context: Context): WorkManager {
        Log.i(Tag, "create: ")
        val configuration = Configuration.Builder().build()
        WorkManager.initialize(context, configuration)
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
       return mutableListOf()
    }
}