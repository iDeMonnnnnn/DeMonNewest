package com.demon.demonnewest.module.views.animation

import android.animation.AnimatorSet
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.demon.base.mvvm.BaseVBFragment
import com.demon.base.utils.ext.*
import com.demon.demonnewest.databinding.FragmentAnimBinding


/**
 * @author DeMon
 * Created on 2022/5/16.
 * E-mail idemon_liu@qq.com
 * Desc: 属性动画
 */
class ValueFragment : BaseVBFragment<FragmentAnimBinding>() {

    private var lineAnimator: ValueAnimator? = null


    override fun initLazyData() {
        bindingRun {
            btn1.setOnClickThrottleFirst {
                iv.toVisible
                bigView.toGone
                smallView.toGone
                raAnimator()
            }

            btn2.setOnClickThrottleFirst {
                iv.toVisible
                bigView.toGone
                smallView.toGone
                lineAnimator()
            }


            btn3.setOnClickThrottleFirst {
                iv.toVisible
                bigView.toGone
                smallView.toGone
                scaleAnimator()
            }

            btn4.setOnClickThrottleFirst {
                iv.toVisible
                bigView.toGone
                smallView.toGone
                alphaAnimator()
            }

            btn5.setOnClickThrottleFirst {
                iv.toGone
                bigView.toVisible
                smallView.toVisible
                smallView.post {
                    changeAnimator()
                }
            }
        }
    }

    private fun lineAnimator() {
        val height = binding.root.height
        lineAnimator = ValueAnimator.ofInt(0, height / 4, height / 2, height * 3 / 4, height, height / 2)
        lineAnimator?.duration = 3000
        lineAnimator?.addUpdateListener {
            moveView(binding.iv, screenWidth / 2, it.animatedValue.parseInt())
        }
        lineAnimator?.start()
    }


    //定义一个修改View位置的方法
    private fun moveView(view: View, rawX: Int, rawY: Int) {
        val left: Int = rawX - view.width / 2
        val top: Int = rawY - view.height
        val width = left + view.width
        val height = top + view.height
        Log.i(TAG, "moveView: left=$left,top=$top,width=$width,height=$height")
        view.layout(left, top, width, height)
    }


    private fun scaleAnimator() {
        val scaleSet = AnimatorSet()
        val valueAnimatorSmall = ValueAnimator.ofFloat(2.0f, 0.5f)
        valueAnimatorSmall.duration = 3000

        val valueAnimatorLarge = ValueAnimator.ofFloat(0.5f, 1.0f)
        valueAnimatorLarge.duration = 3000

        valueAnimatorSmall.addUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            binding.iv.scaleX = scale
            binding.iv.scaleY = scale
        }
        valueAnimatorLarge.addUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            binding.iv.scaleX = scale
            binding.iv.scaleY = scale
        }

        scaleSet.play(valueAnimatorLarge).after(valueAnimatorSmall)
        scaleSet.start()

    }

    private fun raAnimator() {
        val rValue = ValueAnimator.ofFloat(0f, 360f)
        rValue.duration = 3000L
        rValue.addUpdateListener { animation ->
            val rotateValue = animation.animatedValue as Float
            binding.iv.rotation = rotateValue
        }
        rValue.interpolator = DecelerateInterpolator()
        rValue.start()
    }


    private fun alphaAnimator() {
        val rValue = ValueAnimator.ofFloat(0f, 1f)
        rValue.duration = 3000L
        rValue.addUpdateListener { animation ->
            val fractionValue = animation.animatedValue as Float
            binding.iv.alpha = fractionValue
        }
        rValue.interpolator = DecelerateInterpolator()
        rValue.start()
    }


    private fun changeAnimator() {
        bindingRun {

            bigView.bringToFront()

            val bigw = bigView.width
            val bigh = bigView.height
            val bigx: Int = bigw / 2
            val bigy: Int = bigView.top + bigh / 2

            val smallw = smallView.width
            val smallh = smallView.height
            val smallx: Int = bigw - 16.intDp - smallw / 2
            val smally: Int = bigView.top + 16.intDp + smallh / 2

            val pairEvaluator = TypeEvaluator<Pair<Int, Int>> { fraction, startValue, endValue ->
                Log.i(TAG, "smallScale: fraction=$fraction,startValue=$startValue,endValue=$endValue")
                val first = startValue.first * (1 - fraction) + fraction * endValue.first
                val second = startValue.second * (1 - fraction) + fraction * endValue.second
                Pair(first.parseInt(), second.parseInt())
            }
            val animatorSet = AnimatorSet()
            val bigScale = ValueAnimator.ofObject(pairEvaluator, Pair(bigw, bigh), Pair(smallw, smallh))
            bigScale.duration = 1000
            bigScale.addUpdateListener { animation ->
                val pair = animation.animatedValue as Pair<Int, Int>
                Log.i(TAG, "bigScale: $pair")
                bigView.layoutParams.width = pair.first
                bigView.layoutParams.height = pair.second
                bigView.requestLayout()
            }

            val bigTranslate = ValueAnimator.ofObject(pairEvaluator, Pair(bigx, bigy), Pair(smallx, smally))
            bigTranslate.duration = 1000
            bigTranslate.addUpdateListener { animation ->
                val pair = animation.animatedValue as Pair<Int, Int>
                Log.i(TAG, "bigTranslate: $pair")
                translateView(bigView, pair.first, pair.second)
            }

            val smallTranslate = ValueAnimator.ofObject(pairEvaluator, Pair(smallx, smally), Pair(bigx, bigy))
            smallTranslate.duration = 1000
            smallTranslate.addUpdateListener { animation ->
                val pair = animation.animatedValue as Pair<Int, Int>
                Log.i(TAG, "smallTranslate: $pair")
                translateView(smallView, pair.first, pair.second)
            }
            val smallScale = ValueAnimator.ofObject(pairEvaluator, Pair(smallw, smallh), Pair(bigw, bigh))
            smallScale.duration = 1000
            smallScale.addUpdateListener { animation ->
                val pair = animation.animatedValue as Pair<Int, Int>
                Log.i(TAG, "smallScale: $pair")
                smallView.setLayoutParams(pair.first, pair.second, 0, 0, 0, 0)
                smallView.requestLayout()
            }


            animatorSet.play(bigTranslate).after(bigScale).after(smallScale)
            animatorSet.startDelay = 1000
            animatorSet.start()
        }
    }


    //定义一个修改View位置的方法
    private fun translateView(view: View, rawX: Int, rawY: Int) {
        val left: Int = rawX - view.width / 2
        val top: Int = rawY - view.height / 2
        val width = left + view.width
        val height = top + view.height
        Log.i(TAG, "translateView: left=$left,top=$top,width=$width,height=$height")
        view.layout(left, top, width, height)
    }

    override fun onPause() {
        Log.i(TAG, "onPause: ")
        super.onPause()
        lineAnimator?.cancel()
        lineAnimator?.removeAllUpdateListeners()
    }

}