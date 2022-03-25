package com.demon.demonjetpack.module.views.widget.motion

import androidx.constraintlayout.motion.widget.MotionLayout
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmFragment
import com.demon.demonjetpack.databinding.FragmentTextScatteredBinding

/**
 * @author DeMon
 * Created on 2021/1/28.
 * E-mail 757454343@qq.com
 * Desc:
 */
class TextScatteredFragment : MvvmFragment<FragmentTextScatteredBinding, BaseViewModel>() {
    override fun initData() {
        binding.motionLayout.transitionToEnd()
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