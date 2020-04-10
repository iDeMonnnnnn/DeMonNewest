package com.demon.easyjetpack.module.main

import android.util.Log
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.easyjetpack.R
import com.demon.easyjetpack.data.Constants
import com.demon.easyjetpack.data.RouterConst
import com.demon.easyjetpack.ext.getCurrentProcessName
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = RouterConst.ACT_MAIN)
class MainActivity : MvvmActivity<MainViewModel>() {

    override fun setupLayoutId(): Int = R.layout.activity_main


    override fun init() {
        tvProgress.text = getCurrentProcessName()
        
        btn.setOnClickListener {
            ARouter.getInstance().build(RouterConst.ACT_FRAGMENT).navigation()
        }

        btn1.setOnClickListener {
            mViewModel.showDialog()
        }

        btn2.setOnClickListener {
            ARouter.getInstance().build(RouterConst.ACT_DAGGER).navigation()
        }

        btn3.setOnClickListener {
            ARouter.getInstance().build(RouterConst.ACT_ROOM).navigation()
        }

        btn4.setOnClickListener {
            ARouter.getInstance().build(RouterConst.ACT_MULTIPROGRESS).navigation()
        }

        LiveEventBus.get(Constants.EVENT_BUS, String::class.java).observe(this, Observer { t ->
            Log.i(TAG, "普通消息：{ $t }")
        })

        LiveEventBus.get(Constants.MULTI_PROGRESS, String::class.java).observe(this, Observer { t ->
            Log.i(TAG, "跨进程消息：{ $t }")
        })
    }


    override fun initViewModel() {
    }

}
