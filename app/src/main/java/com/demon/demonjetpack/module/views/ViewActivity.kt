package com.demon.demonjetpack.module.views

import com.alibaba.android.arouter.launcher.ARouter
import com.demon.basemvvm.intent.toActivity
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.base.data.RouterConst
import com.demon.demonjetpack.databinding.ActivityViewBinding
import com.demon.demonjetpack.module.views.lighter.LighterActivity
import com.demon.demonjetpack.module.views.motion.MotionActivity

class ViewActivity : MvvmActivity<ActivityViewBinding, BaseViewModel>() {

    override fun init() {

        binding.btnViewBinding.setOnClickListener {
            ARouter.getInstance().build(RouterConst.ACT_VIEWBINDING).navigation()
        }

        binding.btnMotion.setOnClickListener {
            toActivity(MotionActivity::class.java)
        }


        binding.btnLighter.setOnClickListener {
            toActivity(LighterActivity::class.java)
        }
    }

}