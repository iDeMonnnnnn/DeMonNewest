package com.demon.demonjetpack.module.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.demon.basemvvm.helper.BroadcastHelper
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.basemvvm.utils.Tag
import com.demon.basemvvm.utils.setOnClickThrottleFirst
import com.demon.demonjetpack.base.data.Constants
import com.demon.demonjetpack.base.data.RouterConst
import com.demon.demonjetpack.base.ext.toast
import com.demon.demonjetpack.base.util.AppUtils
import com.demon.demonjetpack.bean.HomeEntity
import com.demon.demonjetpack.databinding.ActivityHomeBinding
import com.demon.demonjetpack.module.ara.ActResultActivity
import com.demon.demonjetpack.module.dp.AudioActivity
import com.demon.demonjetpack.module.views.DoodleActivity
import com.demon.demonjetpack.module.views.LighterActivity
import com.demon.demonjetpack.module.views.MotionActivity
import com.demon.demonjetpack.module.views.VbActivity
import com.google.android.material.snackbar.Snackbar
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@Route(path = RouterConst.ACT_HOME)
@AndroidEntryPoint
class HomeActivity : MvvmActivity<ActivityHomeBinding, HomeViewModel>(), OnItemClickListener {

    @Inject
    lateinit var broadcastHelper: BroadcastHelper
    private val homeDatas = arrayListOf(
        HomeEntity("JetPack"),
        HomeEntity("MVVM", RouterConst.ACT_FRAGMENT),
        HomeEntity("Hilt", RouterConst.ACT_DAGGER),
        HomeEntity("Room", RouterConst.ACT_ROOM),
        HomeEntity("Paging3", RouterConst.ACT_PAGING),
        HomeEntity("DataStore", ""),
        HomeEntity("WorkManager", RouterConst.ACT_WORKER),
        HomeEntity("Activity Result API", ActResultActivity::class.java),
        HomeEntity("框架&使用实例"),
        HomeEntity("多进程通信", RouterConst.ACT_MULTIPROGRESS),
        HomeEntity("自定义View"),
        HomeEntity("Motion动画", MotionActivity::class.java),
        HomeEntity("ViewBinding", VbActivity::class.java),
        HomeEntity("引导高亮", LighterActivity::class.java),
        HomeEntity("涂鸦马赛克", DoodleActivity::class.java),
        HomeEntity("设计模式"),
        HomeEntity("工厂模式", AudioActivity::class.java),
    )

    private val adapter by lazy {
        HomeAdapter(homeDatas).apply {
            setOnItemClickListener(this@HomeActivity)
        }
    }

    override fun init() {

        immersionBar {
            titleBar(binding.toolbar)
        }

        bindingRun {
            rvMenu.adapter = adapter
            fab.setOnClickThrottleFirst {
                Snackbar.make(it, "当前渠道:${AppUtils.getChannel(mContext, "apkchannel")}", Snackbar.LENGTH_LONG).show()
            }
        }

        /*binding.tvProgress.text = "当前进程:${AppUtils.getCurrentProcessName(this)}"
        binding.tvChannel.text = "当前渠道:${AppUtils.getChannel(this, "apkchannel")}"
        mViewModel.showDialog()
        binding.btn.setOnClickListener {
            //ARouter.getInstance().build(RouterConst.ACT_FRAGMENT).navigation()
            toActivity(FragActivity::class.java, "params" to arrayListOf("鸿洋", "郭霖"))
        }

        binding.btnDagger.setOnClickListener {
            toActivityForResult(HiltActivity::class.java, "params" to "hello world") {
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
                DataStoreHelper.put("data_string", "hello world")
                DataStoreHelper.put("data_long", System.currentTimeMillis())
                delay(1000)
                Log.i(Tag, "init: ${DataStoreHelper.get("data_long", 0L)}")
                Log.i(Tag, "init: ${DataStoreHelper.get("data_string", "String")}")
            }
        }

        binding.btnWorkManager.setOnClickListener {
            ARouter.getInstance().build(RouterConst.ACT_WORKER).navigation()
        }

        binding.btnView.setOnClickListener {
            toActivity(ViewsActivity::class.java)
        }

        binding.btnDP.setOnClickListener {
            toActivity(DpActivity::class.java)
        }*/

        LiveEventBus.get(Constants.EVENT_BUS, String::class.java).observe(this, Observer { t ->
            Log.i(Tag, "普通消息：{ $t }")
            t.toast()
        })

        LiveEventBus.get(Constants.MULTI_PROGRESS, String::class.java).observe(this, Observer { t ->
            Log.i(Tag, "跨进程消息：{ $t }")
            t.toast()
        })


        broadcastHelper.addAction(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Constants.BROADCAST1 -> {
                        "${intent.getLongExtra(BroadcastHelper.RESULT, 0)}".toast()
                    }
                    Constants.BROADCAST2 -> {
                        intent.getStringExtra(BroadcastHelper.RESULT)?.toast()
                    }
                }
            }
        }, Constants.BROADCAST1, Constants.BROADCAST2)

    }


    override fun initViewModel() {
    }


    override fun onDestroy() {
        super.onDestroy()
        broadcastHelper.destroy(Constants.BROADCAST1, Constants.BROADCAST2)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = adapter.data[position] as HomeEntity
        if (!item.isHeader) {
            when {
                item.router.isNotBlank() -> {
                    ARouter.getInstance().build(item.router).navigation()
                }
                item.activity != null -> {
                    startActivity(Intent(this@HomeActivity, item.activity))
                }
                else -> {
                    "Click~".toast()
                }
            }
        }


    }


}
