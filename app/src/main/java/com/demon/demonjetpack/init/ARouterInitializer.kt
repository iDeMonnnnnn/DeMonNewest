package com.demon.demonjetpack.init

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.alibaba.android.arouter.launcher.ARouter
import com.demon.basemvvm.utils.Tag
import com.demon.demonjetpack.BuildConfig

/**
 * @author DeMon
 * Created on 2021/1/19.
 * E-mail 757454343@qq.com
 * Desc:
 */
class ARouterInitializer : Initializer<ARouter> {
    override fun create(context: Context): ARouter {
        Log.i(Tag, "create: ")
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(context.applicationContext as Application)       // 尽可能早，推荐在Application中初始化
        return ARouter.getInstance()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(WorkManagerInitializer::class.java)
    }
}