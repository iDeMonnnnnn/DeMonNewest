package com.demon.easyjetpack.module.dagger

import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.helper.BroadcastHelper
import com.demon.basemvvm.intent.extraAct
import com.demon.basemvvm.intent.finishResult
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.easyjetpack.R
import com.demon.easyjetpack.base.data.Constants
import com.demon.easyjetpack.base.data.RouterConst
import com.jeremyliao.liveeventbus.LiveEventBus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dagger_test.*
import javax.inject.Inject

@Route(path = RouterConst.ACT_DAGGER)
@AndroidEntryPoint
class DaggerTestActivity : MvvmActivity<BaseViewModel>() {
    private val str by extraAct<String>("params")

    @Inject
    protected lateinit var helper: DoHelper

    @Inject
    lateinit var broadcastHelper: BroadcastHelper

    override fun setupLayoutId(): Int = R.layout.activity_dagger_test

    override fun init() {

        name.text = helper.name

        btn.setOnClickListener {
            time.text = "${helper.getNowTime()}"
        }

        btn1.setOnClickListener {
            ha.text = helper.getHa()
        }

        btnDagger.setOnClickListener {
            provides.text = helper.getGson()
        }

        btnRoom.setOnClickListener {
            LiveEventBus.get(Constants.EVENT_BUS).post(str)
            finishResult("key" to "123456")
        }

        btnBroadcast1.setOnClickListener {
            broadcastHelper.sendBroadcast(Constants.BROADCAST1, "123456")
        }


        btnBroadcast2.setOnClickListener {
            broadcastHelper.sendBroadcast(Constants.BROADCAST2, "abcdefg")
        }
    }

    override fun initViewModel() {

    }
}
