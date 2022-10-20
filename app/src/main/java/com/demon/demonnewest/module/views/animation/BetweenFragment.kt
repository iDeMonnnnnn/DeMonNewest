package com.demon.demonnewest.module.views.animation

import android.util.Log
import android.view.animation.AnimationUtils
import com.demon.base.mvvm.BaseVBFragment
import com.demon.base.utils.ext.setOnClickThrottleFirst
import com.demon.demonnewest.R
import com.demon.demonnewest.databinding.FragmentAnimBinding

/**
 * @author DeMon
 * Created on 2022/5/16.
 * E-mail idemon_liu@qq.com
 * Desc: 补间动画
 */
class BetweenFragment : BaseVBFragment<FragmentAnimBinding>() {

    override fun initLazyData() {
        bindingRun {
            btn1.setOnClickThrottleFirst {
                iv.clearAnimation()
                iv.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.anim_rotate))
            }

            btn2.setOnClickThrottleFirst {
                iv.clearAnimation()
                iv.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.anim_translate))
            }


            btn3.setOnClickThrottleFirst {
                iv.clearAnimation()
                iv.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.anim_scale))
            }

            btn4.setOnClickThrottleFirst {
                iv.clearAnimation()
                iv.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.anim_alpha))
            }

            btn5.setOnClickThrottleFirst {
                iv.clearAnimation()
                iv.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.anim_group))
            }
        }
    }



    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
        binding.iv.clearAnimation()
    }

}