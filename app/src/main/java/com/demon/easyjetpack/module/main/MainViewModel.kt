package com.demon.easyjetpack.module.main

import androidx.lifecycle.viewModelScope
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.easyjetpack.ext.toast
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    fun showDialog() {
        viewModelScope.launch {
            "Hello World".toast(application)
        }
    }

}