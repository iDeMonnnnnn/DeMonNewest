package com.demon.demonnewest.module.views.motion

import androidx.constraintlayout.motion.widget.MotionLayout
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmFragment
import com.demon.demonnewest.BuildConfig
import com.demon.demonnewest.databinding.FragmentTextScatteredBinding

/**
 * @author DeMon
 * Created on 2021/1/28.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class TextScatteredFragment : MvvmFragment<FragmentTextScatteredBinding, BaseViewModel>() {
    override fun initData() {
        binding.motionLayout.setDebugMode(
            if (BuildConfig.DEBUG) {
                MotionLayout.DEBUG_SHOW_PATH
            } else {
                MotionLayout.DEBUG_SHOW_NONE
            }
        )
        binding.root.post {
            binding.motionLayout.transitionToEnd()
        }
        binding.motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                binding.shimmerLayout.startShimmer()
                binding.shimmerLayout.postDelayed({
                    if (isVisible)
                        binding.shimmerLayout.stopShimmer()
                }, 3000)
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }
        })
    }



    override fun onDestroyView() {
        binding.shimmerLayout.stopShimmer()
        super.onDestroyView()
    }
}