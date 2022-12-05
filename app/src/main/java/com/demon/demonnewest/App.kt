package com.demon.demonnewest

import com.demon.base.BaseApp
import com.demon.base.utils.ext.isMainProcess
import com.demon.qfsolution.QFHelper
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mars.xlog.Log
import dagger.hilt.android.HiltAndroidApp

/**
 * @author DeMon
 * Created on 2020/4/10.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
@HiltAndroidApp
class App : BaseApp() {

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
        if (isMainProcess()) {
            QFHelper.init(this, "fileProvider")
        }
        LiveEventBus.config().setContext(applicationContext)
    }

}