package com.demon.demonnewest.module.views.animation

import android.animation.ValueAnimator
import android.graphics.drawable.AnimationDrawable
import androidx.core.content.ContextCompat
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmFragment
import com.demon.base.utils.click.setOnClickThrottleFirst
import com.demon.base.utils.ext.getCompatColor
import com.demon.demonnewest.databinding.FragmentAnimationBinding
import com.tencent.mars.xlog.Log


/**
 * @author DeMon
 * Created on 2022/5/16.
 * E-mail idemon_liu@qq.com
 * Desc: 属性动画
 */
class ValueFragment : MvvmFragment<FragmentAnimationBinding, BaseViewModel>() {
    var valueAnimator: ValueAnimator? = null

    override fun initData() {
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