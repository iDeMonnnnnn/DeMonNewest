package com.demon.demonjetpack.module.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.demon.basemvvm.helper.BroadcastHelper
import com.demon.basemvvm.helper.DataStoreHelper
import com.demon.basemvvm.intent.get
import com.demon.basemvvm.intent.toActivity
import com.demon.basemvvm.intent.toActivityForResult
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.basemvvm.utils.Tag
import com.demon.basemvvm.utils.launchUI
import com.demon.basemvvm.utils.mmkv
import com.demon.demonjetpack.base.data.Constants
import com.demon.demonjetpack.base.data.RouterConst
import com.demon.demonjetpack.base.ext.getChannel
import com.demon.demonjetpack.base.ext.getCurrentProcessName
import com.demon.demonjetpack.base.ext.toast
import com.demon.demonjetpack.databinding.ActivityMainBinding
import com.demon.demonjetpack.module.dagger.DaggerTestActivity
import com.demon.demonjetpack.module.dp.DpActivity
import com.demon.demonjetpack.module.fragment.FragActivity
import com.demon.demonjetpack.module.views.ViewActivity
import com.jeremyliao.liveeventbus.LiveEventBus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject


@Route(path = RouterConst.ACT_MAIN)
@AndroidEntryPoint
class MainActivity : MvvmActivity<ActivityMainBinding, MainViewModel>() {


    @Inject
    lateinit var broadcastHelper: BroadcastHelper

    override fun onStart() {
        super.onStart()
    }

    override fun init() {
        binding.tvProgress.text = "当前进程:${getCurrentProcessName()}"
        binding.tvChannel.text = "当前渠道:${getChannel("apkchannel")}"
        mViewModel.showDialog()
        binding.btn.setOnClickListener {
            //ARouter.getInstance().build(RouterConst.ACT_FRAGMENT).navigation()
            toActivity(FragActivity::class.java, "params" to arrayListOf("鸿洋", "郭霖"))
        }

        binding.btnDagger.setOnClickListener {
            toActivityForResult(DaggerTestActivity::class.java, "params" to "hello world") {
                Log.i(Tag, "init: " + it?.get("key", ""))
            }
        }

        binding.btnRoom.setOnClickListener {
            ARouter.getInstance().build(RouterConst.ACT_ROOM).navigation()
        }

        binding.btnMultiProgress.setOnClickListener {
            ARouter.getInstance().build(RouterConst.ACT_MULTIPROGRESS).navigation()
        }

        binding.btnPaging.setOnClickListener { ARouter.getInstance().build(RouterConst.ACT_PAGING).navigation() }

        binding.btnDataStore.setOnClickListener {
            mmkv?.encode("data_string", "hello world")
            mmkv?.encode("data_long", System.currentTimeMillis())

            Log.i(Tag, "init: ${mmkv?.decodeString("data_string", "")}")
            Log.i(Tag, "init: ${mmkv?.decodeLong("data_long", 0L)}")
            lifecycleScope.launchUI {
                DataStoreHelper.instance.put("data_string", "hello world")
                DataStoreHelper.instance.put("data_long", System.currentTimeMillis())
                delay(1000)
                Log.i("DataStoreHelper", "init: ${DataStoreHelper.instance.get("data_long", 0L)}")
                Log.i("DataStoreHelper", "init: ${DataStoreHelper.instance.get("data_string", "String")}")
            }
        }

        binding.btnWorkManager.setOnClickListener {
            ARouter.getInstance().build(RouterConst.ACT_WORKER).navigation()
        }

        binding.btnView.setOnClickListener {
            toActivity(ViewActivity::class.java)
        }

        binding.btnDP.setOnClickListener {
            toActivity(DpActivity::class.java)
        }

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
                        "${intent.getLongExtra(BroadcastHelper.RESULT, 0)}".toast(mContext)
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
