package com.demon.base.mvvm

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * @author DeMonnnnnn
 * date 2022/9/23
 * email liu_demon@qq.com
 * desc
 */
abstract class BaseVBActivity<VB : ViewBinding> : MvvmActivity<VB, BaseViewModel>() {

   override fun providerVM(): BaseViewModel = ViewModelProvider(this)[BaseViewModel::class.java]

}