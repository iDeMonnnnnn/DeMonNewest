package com.demon.base.utils

import android.view.View
import com.github.satoshun.coroutine.autodispose.view.autoDisposeScope
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
 */
fun View.setOnClickThrottleFirst(block: (View) -> Unit) {
    this.clicks().sample(500).onEach {
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
        view.clicks().sample(500).onEach {
            block(view)
        }.launchIn(view.autoDisposeScope)
    }
}


/**
 * 多个控件设置setOnClickListener(this)，防止重复点击
 */
fun View.OnClickListener.setOnClickThrottleFirst(vararg ids: View?) {
    ids.filterNotNull().forEach { view ->
        view.clicks().sample(500).onEach {
            onClick(view)
        }.launchIn(view.autoDisposeScope)
    }
}





