package com.demon.base.utils.ext

import android.view.View
import com.github.satoshun.coroutine.autodispose.view.autoDisposeScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import reactivecircus.flowbinding.android.view.clicks

/**
 * @author DeMon
 * Created on 2021/8/31.
 * E-mail idemon_liu@qq.com
 * Desc:根据Flow流控制View的重复点击事件
 */

/**
 * 单个控件设置点击事件，防止重复点击
 * tips:
 * ViewPager2由于基于RecycleView实现，Fragment如果单例懒加载，页面切换autoDisposeScope会触发onViewDetachedFromWindow，
 * 导致Fragment切换回来无法点击。有两种解决方案：
 * 1. viewPager2.offscreenPageLimit = list.size，禁止ViewPager2切换销毁
 * 2. Fragment懒加载时，在onResume中调用[setOnClickThrottleFirst]
 */
fun View.setOnClickThrottleFirst(block: (View) -> Unit) {
    this.clicks().sample(500).flowOn(Dispatchers.Main).onEach {
        block(this)
    }.launchIn(autoDisposeScope)
}

/**
 * 多个控件设置同一点击事件，防止重复点击
 */
fun setOnClickThrottleFirst(
    vararg ids: View?,
    block: (View) -> Unit
) {
    ids.filterNotNull().forEach { view ->
        view.clicks().sample(500).flowOn(Dispatchers.Main).onEach {
            block(view)
        }.launchIn(view.autoDisposeScope)
    }
}


/**
 * 多个控件设置setOnClickListener(this)，防止重复点击
 */
fun View.OnClickListener.setOnClickThrottleFirst(vararg ids: View?) {
    ids.filterNotNull().forEach { view ->
        view.clicks().sample(500).flowOn(Dispatchers.Main).onEach {
            onClick(view)
        }.launchIn(view.autoDisposeScope)
    }
}





