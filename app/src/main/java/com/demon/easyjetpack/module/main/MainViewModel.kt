package com.demon.easyjetpack.module.main

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.easyjetpack.base.ext.toast
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(@ApplicationContext var context: Context) : BaseViewModel() {

    fun showDialog() {
        viewModelScope.launch {
            "Hello World".toast(context)
        }
    }

}