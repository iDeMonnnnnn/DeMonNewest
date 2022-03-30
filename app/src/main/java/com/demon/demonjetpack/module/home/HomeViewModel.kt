package com.demon.demonjetpack.module.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.utils.ext.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var application: Application

    fun showDialog() {
        viewModelScope.launch {
            "Hello World".toast()
        }
    }

}