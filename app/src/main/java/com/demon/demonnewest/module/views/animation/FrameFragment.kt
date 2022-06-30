package com.demon.demonnewest.module.views.animation

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
 * Desc: 帧动画
 */
class FrameFragment : MvvmFragment<FragmentAnimationBinding, BaseViewModel>() {

    var animationDrawable: AnimationDrawable? = null

    override fun initData() {
        binding.btnStart.setOnClickThrottleFirst {
            animationDrawable()
        }
    }

    fun animationDrawable() {
        if (animationDrawable == null) {
            animationDrawable = AnimationDrawable()
            for (i in 0 until 45) {
                val id = resources.getIdentifier("frame_$i", "drawable", requireContext().packageName)
                val drawable = ContextCompat.getDrawable(requireContext(), id)
                drawable?.run { animationDrawable?.addFrame(this, 100) }
            }
            binding.iv.setImageDrawable(animationDrawable)
        }
        animationDrawable?.start()
    }


    override fun onPause() {
        Log.i(TAG, "onPause: ")
        super.onPause()
        animationDrawable?.stop()
    }

}