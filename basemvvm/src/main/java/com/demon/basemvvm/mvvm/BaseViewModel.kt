package com.demon.basemvvm.mvvm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

open class BaseViewModel : ViewModel(), LifecycleObserver {
    val errLiveData = MutableLiveData<String>()
    val loadingData = MutableLiveData<Boolean>()

    @ExperimentalCoroutinesApi
    protected fun <T> MutableLiveData<out T>.toFlowLoading(block: suspend () -> T) {
        flow { emit(block()) }
            .onStart {
                loadingData.value = true
            }.onEach {
                this.value = it
            }.onCompletion {
                loadingData.value = false
            }.catch {
                errLiveData.value = it.message
            }.launchIn(viewModelScope)
    }

    @ExperimentalCoroutinesApi
    protected fun <T> MutableLiveData<out T>.toFlow(block: suspend () -> T) {
        flow { emit(block()) }
            .onEach {
                this.value = it
            }.onCompletion {
                loadingData.value = false
            }.catch {
                errLiveData.value = it.message
            }.launchIn(viewModelScope)
    }

}