package com.demon.demonjetpack

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.demon.basemvvm.MvvmApp
import com.jeremyliao.liveeventbus.LiveEventBus
import dagger.hilt.android.HiltAndroidApp

/**AppComponent
 * @author DeMon
 * Created on 2020/4/10.
 * E-mail 757454343@qq.com
 * Desc:
 */
@HiltAndroidApp
class App : MvvmApp() {

    companion object {
        lateinit var instance: Application
        lateinit var appContext: Context
    }


    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: ")
        instance = this
        appContext = applicationContext
        LiveEventBus.config().setContext(applicationContext)
    }

}