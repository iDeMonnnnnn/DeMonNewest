package com.demon.demonnewest.module

import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.base.utils.ext.toActivity
import com.demon.demonnewest.BuildConfig
import com.demon.demonnewest.databinding.ActivityStartBinding
import com.demon.demonnewest.module.home.HomeActivity

/**
 * @author DeMonnnnnn
 * date 2022/7/29
 * email liu_demon@qq.com
 * desc
 */
class StartActivity : MvvmActivity<ActivityStartBinding, BaseViewModel>() {


    override fun initData() {
/*        binding.motionLayout.setDebugMode(
            if (BuildConfig.DEBUG) {
                MotionLayout.DEBUG_SHOW_PATH
            } else {
                MotionLayout.DEBUG_SHOW_NONE
            }
        )*/
        binding.motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                Log.i(TAG, "onTransitionStarted: ")
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                Log.i(TAG, "onTransitionCompleted: ")
                binding.shimmerLayout.startShimmer()
                binding.shimmerLayout.postDelayed({
                    toActivity<HomeActivity>()
                    finish()
                }, 1000)
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }
        })

        binding.root.post {
            binding.motionLayout.transitionToEnd()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.shimmerLayout.stopShimmer()
    }

}