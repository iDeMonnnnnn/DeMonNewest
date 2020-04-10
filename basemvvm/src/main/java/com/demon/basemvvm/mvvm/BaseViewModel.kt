package com.demon.basemvvm.mvvm

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

open class BaseViewModel : ViewModel(), LifecycleObserver {
    protected val TAG = javaClass.simpleName
    lateinit var mContext:Context
    @Inject
    protected lateinit var application: Application

    val errLiveData = MutableLiveData<String>()

}