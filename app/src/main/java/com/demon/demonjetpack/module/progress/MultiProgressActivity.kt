package com.demon.demonjetpack.module.progress

import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.helper.BroadcastHelper
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.base.data.Constants
import com.demon.demonjetpack.base.data.RouterConst
import com.demon.demonjetpack.base.ext.getCurrentProcessName
import com.demon.demonjetpack.databinding.ActivityMultiProgressBinding
import com.jeremyliao.liveeventbus.LiveEventBus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = RouterConst.ACT_MULTIPROGRESS)
@AndroidEntryPoint
class MultiProgressActivity : MvvmActivity<ActivityMultiProgressBinding, BaseViewModel>() {

    @Inject
    lateinit var broadcastHelper: BroadcastHelper


    override fun init() {

        binding.tv.text = "当前进程:${getCurrentProcessName()}"

        binding.btn.setOnClickListener {
            LiveEventBus.get<String>(Constants.MULTI_PROGRESS).postAcrossProcess("收到进程：${getCurrentProcessName()}的消息！！！")
        }


        binding.btnBroadcast1.setOnClickListener {
            broadcastHelper.sendBroadcast(Constants.BROADCAST1, 123456)
        }


        binding.btnBroadcast2.setOnClickListener {
            broadcastHelper.sendBroadcast(Constants.BROADCAST2, "abcdefg")
        }
    }


}
