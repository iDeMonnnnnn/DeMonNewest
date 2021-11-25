package com.demon.demonjetpack

import android.app.Application
import android.content.Context
import android.util.Log
import com.demon.basemvvm.MvvmApp
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
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

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            MaterialHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(context)
        }
        SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
            layout.run {
                // 显示拖动高度/真实拖动高度（默认0.5，阻尼效果）
                setDragRate(1f)
                // 设置是否开启越界回弹功能（默认true）
                setEnableOverScrollBounce(false)
                // 设置是否在没有更多数据之后 Footer 跟随内容，默认不跟随，如需设置在布局里面加
                setEnableFooterFollowWhenNoMoreData(true)
                setEnableLoadMoreWhenContentNotFull(false)
            }
        }
    }


    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: ")
        instance = this
        appContext = applicationContext
        LiveEventBus.config().setContext(applicationContext)
    }

}