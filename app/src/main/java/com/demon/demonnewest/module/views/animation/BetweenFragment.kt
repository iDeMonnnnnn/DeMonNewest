package com.demon.demonnewest.module.views.animation

import android.view.animation.*
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmFragment
import com.demon.base.utils.click.setOnClickThrottleFirst
import com.demon.demonnewest.databinding.FragmentAnimationBinding

/**
 * @author DeMon
 * Created on 2022/5/16.
 * E-mail idemon_liu@qq.com
 * Desc: 补间动画
 */
class BetweenFragment : MvvmFragment<FragmentAnimationBinding, BaseViewModel>() {

    override fun initData() {
        binding.btnStart.setOnClickThrottleFirst {
            translateAnimation()
        }
    }

    fun translateAnimation() {
        val translateAnimation = TranslateAnimation(0f, 200f, 0f, 200f)
        translateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                scaleAnimation()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        translateAnimation.duration = 3000
        binding.iv.startAnimation(translateAnimation)
    }


    fun scaleAnimation() {
        val scaleAnimation = ScaleAnimation(1f, 3f, 1f, 3f)
        scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                rotateAnimation()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        scaleAnimation.duration = 3000
        binding.iv.startAnimation(scaleAnimation)
    }

    fun rotateAnimation() {
        val rotateAnimation = RotateAnimation(0f, 180f, 0f, 0f)
        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                alphaAnimation()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        rotateAnimation.duration = 3000
        binding.iv.startAnimation(rotateAnimation)
    }

    fun alphaAnimation() {
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.iv.clearAnimation()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        alphaAnimation.duration = 3000
        binding.iv.startAnimation(alphaAnimation)
    }


    override fun onPause() {
        super.onPause()
        binding.iv.clearAnimation()
    }

}