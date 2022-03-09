package com.demon.demonjetpack.module.hilt

import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.intent.extraAct
import com.demon.basemvvm.intent.finishResult
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.basemvvm.utils.setOnClickThrottleFirst
import com.demon.demonjetpack.base.data.Constants
import com.demon.demonjetpack.base.data.RouterConst
import com.demon.demonjetpack.base.hilt.HiltElectricEngine
import com.demon.demonjetpack.databinding.ActivityHiltBinding
import com.jeremyliao.liveeventbus.LiveEventBus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = RouterConst.ACT_DAGGER)
@AndroidEntryPoint
class HiltActivity : MvvmActivity<ActivityHiltBinding, BaseViewModel>() {

    private val str by extraAct<String>("params")

    @Inject
    lateinit var doHelper: DoHelper

    @Inject
    lateinit var haHelper: HaHelper

    @HiltElectricEngine
    @Inject
    lateinit var engine: Engine

    override fun initData() {
        setToolbar("Hilt")


        binding.run {
            btn.setOnClickThrottleFirst {
                text.text = haHelper.getAddress()
            }

            btn1.setOnClickThrottleFirst {
                text.text = "${doHelper.getNowTime()}"
            }

            btn2.setOnClickThrottleFirst {
                text.text = doHelper.getGson()
            }

            btn3.setOnClickThrottleFirst {
                engine.start()
                engine.shutdown()
            }

            btn4.setOnClickListener {
                text.text = HiltUnscoped().getGson()
            }

            btn5.setOnClickThrottleFirst {
                LiveEventBus.get<String>(Constants.EVENT_BUS).post(str)
                finishResult("key" to "123456")
            }
        }


    }

}
