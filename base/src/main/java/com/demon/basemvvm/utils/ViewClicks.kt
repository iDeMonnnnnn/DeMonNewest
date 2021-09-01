package com.demon.basemvvm.utils

import android.view.View
import androidx.annotation.CheckResult
import com.demon.basemvvm.R
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.math.abs

/**
 * @author DeMon
 * Created on 2021/8/31.
 * E-mail 757454343@qq.com
 * Desc:根据Flow流控制View的重复点击事件
 */

/**
 * 单个控件设置点击事件，防止重复点击
 */
fun View.setOnClickThrottleFirst(block: (View) -> Unit) {
    this.clicks().onThrottleFirstEach(this) { block(this) }.launchIn(autoDisposeScope)
}

/**
 * 多个控件设置同一点击事件，防止重复点击
 */
fun setOnClickThrottleFirst(
    vararg ids: View?,
    block: (View) -> Unit
) {
    ids.filterNotNull().forEach { view ->
        view.clicks().onThrottleFirstEach(view) { block(view) }.launchIn(view.autoDisposeScope)
    }
}


/**
 * 多个控件设置setOnClickListener(this)，防止重复点击
 */
fun View.OnClickListener.setOnClickThrottleFirst(vararg ids: View?) {
    ids.filterNotNull().forEach { view ->
        view.clicks().onThrottleFirstEach(view) { onClick(view) }.launchIn(view.autoDisposeScope)
    }
}


fun <T> Flow<T>.onThrottleFirstEach(view: View, action: (View) -> Unit): Flow<T> = onEach {
    val lastEmissionTime = view.getTag(R.id.view_click_time) as? Long ?: 0L
    val currentTime = System.currentTimeMillis()
    if (lastEmissionTime == 0L || abs(currentTime - lastEmissionTime) >= 500L) {
        view.setTag(R.id.view_click_time, currentTime)
        action(view)
    }
}


@CheckResult
private fun listener(
    scope: CoroutineScope,
    emitter: (Unit) -> Unit
) = View.OnClickListener {
    if (scope.isActive) {
        emitter(Unit)
    }
}


/**
 * Create a flow which emits on [View] click events.
 *
 * *Warning:* The created flow uses [View.setOnClickListener]. Only one flow can be used at a time.
 *
 * Example:
 *
 * ```
 * view.clicks()
 *      .onEach { /* handle click */ }
 *      .launchIn(lifecycleScope) // lifecycle-runtime-ktx
 * ```
 */
@CheckResult
fun View.clicks(): Flow<Unit> = channelFlow {
    this@clicks.setTag(R.id.view_click_time, 0L)
    setOnClickListener(listener(this, ::trySend))
    awaitClose { setOnClickListener(null) }
}


