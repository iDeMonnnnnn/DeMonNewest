package com.demon.demonnewest.module.progress

import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.base.utils.helper.BroadcastHelper
import com.demon.base.mvvm.BaseVBActivity
import com.demon.base.utils.SystemUtils
import com.demon.demonnewest.base.data.Constants
import com.demon.demonnewest.base.data.RouterConst
import com.demon.demonnewest.databinding.ActivityMultiProgressBinding
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mars.xlog.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = RouterConst.ACT_MULTIPROGRESS)
@AndroidEntryPoint
class MultiProgressActivity : BaseVBActivity<ActivityMultiProgressBinding>() {

    private val broadcastHelper: BroadcastHelper by lazy {
        BroadcastHelper.instance
    }


    override fun initData() {
        setToolbar("多进程通信")
        Log.i(TAG, "initData: 多进程通信")
        binding.tv.text = "当前进程:${SystemUtils.getCurrentProcessName(this)}"

        binding.btn.setOnClickListener {
            LiveEventBus.get<String>(Constants.MULTI_PROGRESS).postAcrossProcess("收到进程：${SystemUtils.getCurrentProcessName(this)}的消息！！！")
        }


        binding.btnBroadcast1.setOnClickListener {
            broadcastHelper.sendBroadcast(Constants.BROADCAST1, 123456)
        }


        binding.btnBroadcast2.setOnClickListener {
            broadcastHelper.sendBroadcast(Constants.BROADCAST2, "abcdefg")
        }
    }


}
