package com.demon.demonjetpack.module.ara

import com.demon.basemvvm.intent.finishResult
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.basemvvm.utils.setOnClickThrottleFirst
import com.demon.demonjetpack.databinding.ActivityActResultTwoBinding

class ActResultTwoActivity : MvvmActivity<ActivityActResultTwoBinding, BaseViewModel>() {

    override fun initData() {
         binding.btnFinish.setOnClickThrottleFirst {
             finishResult()
         }
    }
}