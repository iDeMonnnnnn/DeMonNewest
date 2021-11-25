package com.demon.demonjetpack.module.dagger

import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.intent.extraAct
import com.demon.basemvvm.intent.finishResult
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.base.data.Constants
import com.demon.demonjetpack.base.data.RouterConst
import com.demon.demonjetpack.databinding.ActivityDaggerTestBinding
import com.jeremyliao.liveeventbus.LiveEventBus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = RouterConst.ACT_DAGGER)
@AndroidEntryPoint
class HiltActivity : MvvmActivity<ActivityDaggerTestBinding, BaseViewModel>() {
    private val str by extraAct<String>("params")

    @Inject
    protected lateinit var helper: DoHelper


    override fun initData() {
        setToolbar("Hilt")
        binding.name.text = helper.name

        binding.btn.setOnClickListener {
            binding.time.text = "${helper.getNowTime()}"
        }

        binding.btn1.setOnClickListener {
            binding.ha.text = helper.getHa()
        }

        binding.btnDagger.setOnClickListener {
            binding.provides.text = helper.getGson()
        }

        binding.btnRoom.setOnClickListener {
            LiveEventBus.get<String>(Constants.EVENT_BUS).post(str)
            finishResult("key" to "123456")
        }
    }

}
