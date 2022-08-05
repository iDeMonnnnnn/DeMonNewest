package com.demon.demonnewest.module.views.motion

import com.tencent.mars.xlog.Log
import android.widget.AbsoluteLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmFragment
import com.demon.demonnewest.databinding.FragmentRedPackageBinding
import com.demon.demonnewest.databinding.MotionRedPackageBinding
import java.util.*

/**
 * @author DeMon
 * Created on 2021/1/29.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class RedPackageFragment : MvvmFragment<FragmentRedPackageBinding, BaseViewModel>() {

    override fun initLazyData() {
        binding.root.post {
            Log.i(TAG, "init: ${binding.root.measuredWidth} ${binding.root.measuredHeight}")

            for (i in 0 until 10) {
                createNewRedPackage()
            }
        }
    }


    fun createNewRedPackage() {
        val width = Random().nextInt(binding.root.measuredWidth - 120)
        val height = Random().nextInt(binding.root.measuredHeight / 2) //最新出现在屏幕中央
        val redBinding = MotionRedPackageBinding.inflate(layoutInflater, binding.root, false)
        val layoutParams = AbsoluteLayout.LayoutParams(binding.root.layoutParams)
        layoutParams.x = width
        layoutParams.y = height
        redBinding.root.layoutParams = layoutParams
        binding.root.addView(redBinding.motionLayout)
        //计算不同高度的持续时长
        redBinding.motionLayout.setTransitionDuration(((binding.root.measuredHeight - height) * 3000) / binding.root.measuredHeight)
        redBinding.motionLayout.transitionToEnd()
        redBinding.motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                //Log.i(TAG, "onTransitionStarted: ")
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, progress: Float) {
                //Log.i(TAG, "onTransitionChange: $progress")
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, p1: Int) {
                Log.i(TAG, "onTransitionCompleted: ")
                binding.root.removeView(motionLayout)
                createNewRedPackage()
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }
        })
    }
}