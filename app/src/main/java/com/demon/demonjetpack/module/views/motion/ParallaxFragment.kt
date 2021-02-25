package com.demon.demonjetpack.module.views.motion

import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmFragment
import com.demon.demonjetpack.databinding.FragmentParallaxBinding
import com.google.android.material.tabs.TabLayout

/**
 * @author DeMon
 * Created on 2021/1/27.
 * E-mail 757454343@qq.com
 * Desc:
 */
class ParallaxFragment : MvvmFragment<FragmentParallaxBinding, BaseViewModel>() {

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

    override fun init() {

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