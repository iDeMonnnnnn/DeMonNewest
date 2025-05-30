package com.demon.demonnewest.module.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.demon.base.mvvm.MvvmActivity
import com.demon.base.utils.SystemUtils
import com.demon.base.utils.ext.Tag
import com.demon.base.utils.ext.setOnClickThrottleFirst
import com.demon.base.utils.ext.toActivity
import com.demon.base.utils.ext.toast
import com.demon.base.utils.helper.BroadcastHelper
import com.demon.demonnewest.base.data.Constants
import com.demon.demonnewest.base.data.RouterConst
import com.demon.demonnewest.bean.HomeEntity
import com.demon.demonnewest.databinding.ActivityHomeBinding
import com.demon.demonnewest.module.camera.CameraXActivity
import com.demon.demonnewest.module.dp.AudioActivity
import com.demon.demonnewest.module.flow.FlowActivity
import com.demon.demonnewest.module.fragment.FragsActivity
import com.demon.demonnewest.module.img.bitmap.BitmapActivity
import com.demon.demonnewest.module.img.glide.ImgLoadActivity
import com.demon.demonnewest.module.list.ListActivity
import com.demon.demonnewest.module.progress.uuid.DeviceIdActivity
import com.demon.demonnewest.module.vetor.VectorActivity
import com.demon.demonnewest.module.views.DoodleActivity
import com.demon.demonnewest.module.views.LighterActivity
import com.demon.demonnewest.module.views.MotionActivity
import com.demon.demonnewest.module.views.VbActivity
import com.demon.demonnewest.module.views.animation.AnimationActivity
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mars.xlog.Log
import dagger.hilt.android.AndroidEntryPoint


@Route(path = RouterConst.ACT_HOME)
@AndroidEntryPoint
class HomeActivity : MvvmActivity<ActivityHomeBinding, HomeViewModel>(), OnItemClickListener {


    private val broadcastHelper: BroadcastHelper by lazy {
        BroadcastHelper.instance
    }

    private val homeDatas = arrayListOf(
        HomeEntity("JetPack"),
        HomeEntity("MVVM", RouterConst.ACT_ARTICLE),
        HomeEntity("Flow", FlowActivity::class.java),
        HomeEntity("Hilt", RouterConst.ACT_DAGGER),
        HomeEntity("Room", RouterConst.ACT_ROOM),
        HomeEntity("Paging3", RouterConst.ACT_PAGING),
        HomeEntity("DataStore VS MMKV", RouterConst.ACT_DATASTORE),
        HomeEntity("WorkManager", RouterConst.ACT_WORKER),
        HomeEntity("CameraX", CameraXActivity::class.java),
        HomeEntity("框架&使用实例"),
        HomeEntity("多进程通信", RouterConst.ACT_MULTIPROGRESS),
        HomeEntity("多样式列表", ListActivity::class.java),
        HomeEntity("Glide图片加载", ImgLoadActivity::class.java),
        HomeEntity("自定义View"),
        HomeEntity("MotionLayout", MotionActivity::class.java),
        HomeEntity("原生动画", AnimationActivity::class.java),
        HomeEntity("ViewBinding", VbActivity::class.java),
        HomeEntity("引导高亮", LighterActivity::class.java),
        HomeEntity("涂鸦马赛克", DoodleActivity::class.java),
        HomeEntity("Vector", VectorActivity::class.java),
        HomeEntity("设计模式"),
        HomeEntity("工厂模式", AudioActivity::class.java),
        HomeEntity("实验"),
        HomeEntity("Fragment可见性", FragsActivity::class.java),
        HomeEntity("设备唯一标识符", DeviceIdActivity::class.java),
        HomeEntity("Bitmap", BitmapActivity::class.java)
    )

    private val adapter by lazy {
        HomeAdapter(homeDatas).apply {
            setOnItemClickListener(this@HomeActivity)
        }
    }

    override fun setupData() {
        SystemUtils.setSystemBarStatus(this)
        bindingRun {
            rvMenu.adapter = adapter
            rvMenu.clipToPadding
            fab.setOnClickThrottleFirst {

            }
        }


        LiveEventBus.get(Constants.EVENT_BUS, String::class.java).observe(this) { t ->
            Log.i(Tag, "普通消息：{ $t }")
            t.toast()
        }

        LiveEventBus.get(Constants.MULTI_PROGRESS, String::class.java).observe(this) { t ->
            Log.i(Tag, "跨进程消息：{ $t }")
            t.toast()
        }

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
                    toActivity(Intent(this@HomeActivity, item.activity))
                }

                else -> {
                    "Click~".toast()
                }
            }
        }


    }


}
