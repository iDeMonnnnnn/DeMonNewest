package com.demon.demonnewest.module

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.demon.base.mvvm.BaseVBActivity
import com.demon.base.utils.ext.toActivity
import com.demon.base.utils.ext.tryCatch
import com.demon.demonnewest.databinding.ActivityStartBinding
import com.demon.demonnewest.module.home.HomeActivity
import com.tencent.mars.xlog.Log

/**
 * @author DeMonnnnnn
 * date 2022/7/29
 * email liu_demon@qq.com
 * desc
 */
@SuppressLint("CustomSplashScreen")
class StartActivity : BaseVBActivity<ActivityStartBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        tryCatch {
            //必须在super.onCreate前
            val splashScreen = installSplashScreen()
            super.onCreate(savedInstanceState)
            //一直停留在SplashScreen启动界面
            splashScreen.setKeepOnScreenCondition { false }
            splashScreen.setOnExitAnimationListener {
                Log.i(TAG, "onCreate: setOnExitAnimationListener ")
                it.remove()
            }

            //部分三星手机非点击icon打开App，SplashScreen有bug
            //可见：https://issuetracker.google.com/issues/197906327?pli=1
        }
    }

    override fun setupData() {/*        binding.motionLayout.setDebugMode(
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