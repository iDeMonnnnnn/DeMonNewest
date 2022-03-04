package com.demon.demonjetpack.module.ara

import com.demon.basemvvm.intent.forActivityResult
import com.demon.basemvvm.intent.pairIntent
import com.demon.basemvvm.intent.toActivity
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.basemvvm.utils.setOnClickThrottleFirst
import com.demon.demonjetpack.databinding.ActivityActResultBinding

class ActResultActivity : MvvmActivity<ActivityActResultBinding, BaseViewModel>() {

    override fun initData() {
        setToolbar("Activity Result API")

        bindingRun {
            btn.setOnClickThrottleFirst {
                toActivity<ActResultTwoActivity>("string" to TAG)
            }

            btn0.setOnClickThrottleFirst {
                forActivityResult(
                    pairIntent<ActResultTwoActivity>(
                        "string" to TAG,
                        "timestamp" to System.currentTimeMillis()
                    ),
                    isCanBack = false
                ) {
                    val str = it?.getStringExtra("string") ?: ""
                    btn0.text = str
                }
            }
        }

    }
}