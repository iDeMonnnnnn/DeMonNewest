package com.demon.base.utils

import android.view.View
import com.demon.base.R
import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * @author DeMon
 * Created on 2021/8/31.
 * E-mail idemon_liu@qq.com
 * Desc:
 */

/**
 * [https://github.com/satoshun/CoroutineAutoDispose](https://github.com/satoshun/CoroutineAutoDispose)
 *
 *
 * [CoroutineScope] tied to this [View]
 *
 * This scope will be canceled when View is detached.
 */
val View.autoDisposeScope: CoroutineScope
    get() {
        val exist = getTag(R.id.autodispose_view_tag) as? CoroutineScope
        if (exist != null) {
            return exist
        }
        val newScope = CoroutineScope(
            SupervisorJob() +
                    Dispatchers.Main +
                    autoDisposeInterceptor()
        )
        setTag(R.id.autodispose_view_tag, newScope)
        return newScope
    }

/**
 * Create a ContinuationInterceptor that follows attach/detach lifecycle of [View].
 */
@Suppress("FunctionName")
fun ViewAutoDisposeInterceptor(view: View): ContinuationInterceptor =
    ViewAutoDisposeInterceptorImpl(view)

/**
 * Create a ContinuationInterceptor that follows attach/detach lifecycle of [View].
 */
fun View.autoDisposeInterceptor(): ContinuationInterceptor =
    ViewAutoDisposeInterceptor(this)

private class ViewAutoDisposeInterceptorImpl(
    private val view: View
) : ContinuationInterceptor {
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        val job = continuation.context[Job]
        if (job != null) {
            view.autoDispose(job)
        }
        return continuation
    }
}

/**
 * [Job] is automatically disposed and follows the attach/detach lifecycle of [View].
 */
fun View.autoDispose(job: Job) {
    val listener = ViewListener(this, job)
    this.addOnAttachStateChangeListener(listener)
}

private class ViewListener(
    private val view: View,
    private val job: Job
) : View.OnAttachStateChangeListener,
    CompletionHandler {
    override fun onViewDetachedFromWindow(v: View) {
        view.removeOnAttachStateChangeListener(this)
        job.cancel()
    }

    override fun onViewAttachedToWindow(v: View) {
        // do nothing
    }

    override fun invoke(cause: Throwable?) {
        view.removeOnAttachStateChangeListener(this)
        job.cancel()
    }
}