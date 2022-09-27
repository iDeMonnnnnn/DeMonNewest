package com.demon.demonnewest.module.views.animation

import android.graphics.drawable.AnimationDrawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.demon.base.mvvm.BaseVBFragment
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmFragment
import com.demon.base.utils.ext.setOnClickThrottleFirst
import com.demon.demonnewest.databinding.FragmentAnimationBinding


/**
 * @author DeMon
 * Created on 2022/5/16.
 * E-mail idemon_liu@qq.com
 * Desc: 帧动画
 */
class FrameFragment : BaseVBFragment<FragmentAnimationBinding>() {

    var animationDrawable: AnimationDrawable? = null

    override fun initLazyData() {
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
        //animationDrawable?.isOneShot = true
        if (animationDrawable?.isRunning == true) {
            animationDrawable?.stop()
        } else {
            animationDrawable?.start()
        }
    }


    override fun onPause() {
        Log.i(TAG, "onPause: ")
        super.onPause()
        animationDrawable?.stop()
    }

}