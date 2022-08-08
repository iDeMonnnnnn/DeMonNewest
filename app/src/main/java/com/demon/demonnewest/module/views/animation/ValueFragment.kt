package com.demon.demonnewest.module.views.animation

import android.animation.ValueAnimator
import android.util.Log
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmFragment
import com.demon.base.utils.ext.setOnClickThrottleFirst
import com.demon.demonnewest.databinding.FragmentAnimationBinding


/**
 * @author DeMon
 * Created on 2022/5/16.
 * E-mail idemon_liu@qq.com
 * Desc: 属性动画
 */
class ValueFragment : MvvmFragment<FragmentAnimationBinding, BaseViewModel>() {
    var valueAnimator: ValueAnimator? = null

    override fun initLazyData() {
        binding.btnStart.setOnClickThrottleFirst {
            valueAnimator()
        }
    }

    fun valueAnimator() {
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(0, 200, 100)
            valueAnimator?.duration = 3000
            valueAnimator?.addUpdateListener {
                val cur = it.animatedValue as Int
                binding.iv.layoutParams.width = cur
                binding.iv.requestLayout()
            }
        }
        valueAnimator?.start()
    }


    override fun onPause() {
        Log.i(TAG, "onPause: ")
        super.onPause()
        valueAnimator?.cancel()
        //valueAnimator?.removeAllUpdateListeners()
    }

}