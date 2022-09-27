package com.demon.demonnewest.module.views.motion

import com.tencent.mars.xlog.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import com.demon.base.mvvm.BaseVBFragment
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmFragment
import com.demon.demonnewest.databinding.FragmentParallaxBinding
import com.google.android.material.tabs.TabLayout

/**
 * @author DeMon
 * Created on 2021/1/27.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class ParallaxFragment : BaseVBFragment<FragmentParallaxBinding>() {

    private val listener = object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            Log.i(TAG, "onTransitionStarted: ")
        }

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, progress: Float) {
            Log.i(TAG, "onTransitionChange: $progress")
        }

        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            Log.i(TAG, "onTransitionCompleted: ")
        }

        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

        }
    }

    override fun initLazyData() {

        binding.motionLayout.transitionToEnd()
        binding.motionLayout.addTransitionListener(listener)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val pos = (tab?.position ?: 0)
                binding.motionLayout.progress = pos.div(3.0f)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

    }



    override fun onDestroyView() {
        binding.motionLayout.removeTransitionListener(listener)
        super.onDestroyView()
    }
}