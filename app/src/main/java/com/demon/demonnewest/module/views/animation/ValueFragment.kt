package com.demon.demonnewest.module.views.animation

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import com.demon.base.mvvm.BaseVBFragment
import com.demon.base.utils.ext.intDp
import com.demon.base.utils.ext.setOnClickThrottleFirst
import com.demon.base.utils.ext.toGone
import com.demon.demonnewest.databinding.FragmentAnimBinding


/**
 * @author DeMon
 * Created on 2022/5/16.
 * E-mail idemon_liu@qq.com
 * Desc: 属性动画
 */
class ValueFragment : BaseVBFragment<FragmentAnimBinding>() {
    var valueAnimator: ValueAnimator? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btn5.toGone
    }

    override fun initLazyData() {
        bindingRun {
            btn1.setOnClickThrottleFirst {

            }
        }
    }

    fun lineAnimator(){

    }

    fun valueAnimator() {
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(0, 200.intDp, 100.intDp)
            valueAnimator?.duration = 3000
            valueAnimator?.addUpdateListener {
                val cur = it.animatedValue as Int
                binding.iv.layoutParams.width = cur
                binding.iv.layoutParams.height = cur
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