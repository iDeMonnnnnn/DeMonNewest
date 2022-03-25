package com.demon.base.mvvm

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

open class BaseViewModel : ViewModel(), LifecycleObserver {
    protected val TAG = this.javaClass.simpleName

    val errLiveData = MutableLiveData<String>()
    val loadingData = MutableLiveData<Boolean>()

    protected fun <T> onFlow(
        showLoading: Boolean = true,
        request: suspend () -> T,
        start: (suspend () -> Unit)? = null,
        each: (suspend (T) -> Unit)? = null,
        completion: (suspend () -> Unit)? = null,
        catch: (suspend (Throwable) -> Unit)? = null
    ) {
        flow { emit(request()) }
            .onStart {
                start?.invoke() ?: let {
                    if (showLoading) loadingData.value = true
                }
            }
            .onEach {
                each?.invoke(it)
            }
            .onCompletion {
                completion?.invoke() ?: let {
                    if (showLoading) loadingData.value = false
                }
            }
            // catch只会处理上游的异常
            .catch { flowCatch ->
                Log.e(TAG, "onFlow: ${flowCatch.message}", flowCatch)
                catch?.invoke(flowCatch) ?: let {
                    errLiveData.value = flowCatch.message
                }
            }
            // 用于执行collect
            .launchIn(viewModelScope)
    }


    protected fun <T> MutableLiveData<out T>.toFlow(showLoading: Boolean = true, block: suspend () -> T) {
        flow { emit(block()) }
            .onStart {
                if (showLoading) loadingData.value = true
            }
            .onEach {
                this.value = it
            }.onCompletion {
                if (showLoading) loadingData.value = false
            }.catch {
                Log.e(TAG, "toFlow: ${it.message}", it)
                errLiveData.value = it.message
            }.launchIn(viewModelScope)
    }

    /**
     * 不需要额外处理，直接调用
     * ```
     * liveData.toFlowRetry(2, {
     *     it is UnknownHostException
     * }) { dataManager.something().getDataOrThrow() }
     * ```
     */
    protected fun <T> MutableLiveData<out Any>.toFlowRetry(
        retries: Long,
        predicate: suspend (cause: Throwable) -> Boolean = { false },
        block: suspend () -> T
    ) {
        flow { emit(block()) }
            .onStart { loadingData.value = true }
            .onEach { this.value = it }
            .retry(retries, predicate)
            .onCompletion { loadingData.value = false }
            // catch只会处理上游的异常
            .catch {
                Log.e(TAG, "toFlowRetry: ${it.message}", it)
                errLiveData.value = it.message
            }
            // 用于执行collect
            .launchIn(viewModelScope)
    }

}