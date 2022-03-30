package com.demon.demonjetpack.module.ara

import com.demon.base.utils.ext.extraAct
import com.demon.base.utils.ext.finishResult
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.base.utils.click.setOnClickThrottleFirst
import com.demon.demonjetpack.databinding.ActivityActResultTwoBinding

class ActResultTwoActivity : MvvmActivity<ActivityActResultTwoBinding, BaseViewModel>() {

    private val string by extraAct<String>("string")

    private val time by extraAct("time", 0L)

    override fun initData() {
        setToolbar("Activity Result API TWO")

        binding.text.text = "key={$string}\ntime={$time}"

        binding.btnFinish.setOnClickThrottleFirst {
            finishResult("string" to TAG)
        }
    }
}