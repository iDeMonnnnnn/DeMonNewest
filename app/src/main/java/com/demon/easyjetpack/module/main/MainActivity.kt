package com.demon.easyjetpack.module.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.demon.basemvvm.helper.BroadcastHelper
import com.demon.basemvvm.helper.DataStoreHelper
import com.demon.basemvvm.intent.get
import com.demon.basemvvm.intent.toActivity
import com.demon.basemvvm.intent.toActivityForResult
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.basemvvm.utils.Tag
import com.demon.easyjetpack.R
import com.demon.easyjetpack.base.data.Constants
import com.demon.easyjetpack.base.data.RouterConst
import com.demon.easyjetpack.base.ext.getCurrentProcessName
import com.demon.easyjetpack.base.ext.toast
import com.demon.easyjetpack.module.dagger.DaggerTestActivity
import com.demon.easyjetpack.module.fragment.FragActivity
import com.jeremyliao.liveeventbus.LiveEventBus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@Route(path = RouterConst.ACT_MAIN)
@AndroidEntryPoint
class MainActivity : MvvmActivity<MainViewModel>() {

    override fun setupLayoutId(): Int = R.layout.activity_main

    @Inject
    lateinit var broadcastHelper: BroadcastHelper

    override fun init() {
        tvProgress.text = getCurrentProcessName()
        mViewModel.showDialog()

        btn.setOnClickListener {
            //ARouter.getInstance().build(RouterConst.ACT_FRAGMENT).navigation()
            toActivity(FragActivity::class.java, "params" to arrayListOf("鸿洋", "郭霖"))
        }

        btnDagger.setOnClickListener {
            toActivityForResult(DaggerTestActivity::class.java, "params" to "hello world") {
                Log.i(Tag, "init: " + it?.get("key", ""))
            }
            Log.i(Tag, "init: ${DataStoreHelper.instance.get(Constants.DATA_STORE, "")}")
        }

        btnRoom.setOnClickListener {
            ARouter.getInstance().build(RouterConst.ACT_ROOM).navigation()
        }

        btn4.setOnClickListener {
            ARouter.getInstance().build(RouterConst.ACT_MULTIPROGRESS).navigation()
        }

        btnPaging.setOnClickListener { ARouter.getInstance().build(RouterConst.ACT_PAGING).navigation() }

        LiveEventBus.get(Constants.EVENT_BUS, String::class.java).observe(this, Observer { t ->
            Log.i(Tag, "普通消息：{ $t }")
            t.toast(mContext)
        })

        LiveEventBus.get(Constants.MULTI_PROGRESS, String::class.java).observe(this, Observer { t ->
            Log.i(Tag, "跨进程消息：{ $t }")
            t.toast(mContext)
        })


        broadcastHelper.addAction(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Constants.BROADCAST1 -> {
                        intent.getStringExtra(BroadcastHelper.RESULT)?.toast(mContext)
                    }
                    Constants.BROADCAST2 -> {
                        intent.getStringExtra(BroadcastHelper.RESULT)?.toast(mContext)
                    }
                }
            }
        }, *Constants.BROADCASTS)
    }


    override fun initViewModel() {
    }


    override fun onDestroy() {
        super.onDestroy()
        broadcastHelper.destroy(*Constants.BROADCASTS)
    }

}
