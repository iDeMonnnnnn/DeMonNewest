package com.demon.base.mvvm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tencent.mars.xlog.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry

/**
 * @author DeMonnnnnn
 * date 2022/9/23
 * email liu_demon@qq.com
 * desc MVVM架构的ViewModel
 */
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
            .flowOn(Dispatchers.IO)
            .onStart {
                start?.invoke() ?: let {
                    if (showLoading) loadingData.value = true
                }
            }.flowOn(Dispatchers.Main)
            .onEach {
                each?.invoke(it)
            }.flowOn(Dispatchers.Main)
            .onCompletion {
                completion?.invoke() ?: let {
                    if (showLoading) loadingData.value = false
                }
            }.flowOn(Dispatchers.Main)
            // catch只会处理上游的异常
            .catch { flowCatch ->
                Log.e(TAG, "onFlow: ${flowCatch.message}", flowCatch)
                catch?.invoke(flowCatch) ?: let {
                    errLiveData.value = flowCatch.message
                }
            }.flowOn(Dispatchers.Main)
            // 用于执行collect
            .launchIn(viewModelScope)
    }


    protected fun <T> MutableLiveData<T>.toFlow(showLoading: Boolean = true, block: suspend () -> T) {
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
    protected fun <T> MutableLiveData<Any>.toFlowRetry(
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