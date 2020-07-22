package com.demon.easyjetpack.module.progress

import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.easyjetpack.R
import com.demon.easyjetpack.data.Constants
import com.demon.easyjetpack.data.RouterConst
import com.demon.easyjetpack.ext.getCurrentProcessName
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.activity_multi_progress.*

@Route(path = RouterConst.ACT_MULTIPROGRESS)
class MultiProgressActivity : MvvmActivity<BaseViewModel>() {

    override fun setupLayoutId(): Int = R.layout.activity_multi_progress

    override fun init() {

        tv.text = getCurrentProcessName()

        btn.setOnClickListener {
            LiveEventBus.get(Constants.MULTI_PROGRESS).postAcrossProcess("收到进程：${getCurrentProcessName()}的消息！！！")
        }
    }


}
