package com.demon.demonnewest.module.vetor

import android.annotation.TargetApi
import android.graphics.drawable.Animatable
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import com.demon.base.mvvm.BaseVBActivity
import com.demon.demonnewest.databinding.ActivityVectorBinding

/**
 * @author DeMon
 * Created on 28/5/23.
 * E-mail demonl@binarywalk.com
 * Desc:
 */
class VectorActivity : BaseVBActivity<ActivityVectorBinding>() {
    private var mAnimatedVectorDrawable: AnimatedVectorDrawable? = null
    override fun setupData() {

    }


    //1
    fun onDoneClicked(view: View) {
        start(view)
    }

    //2
    fun onFaceClicked(view: View) {
        start(view)
    }

    //3
    fun onClockClicked(view: View) {
        start(view)
    }

    //4
    fun onCupClicked(view: View) {
        start(view)
    }

    //5
    fun onCircleClicked(view: View) {
        start(view)
//		repeat(view);
    }

    //6
    fun onPointClicked(view: View) {
        start(view)
//		repeat(view);
    }

    //7
    fun onPendulumPointClicked(view: View) {
        start(view)
    }

    private fun start(view: View) {
        val imageView = view as ImageView
        val animatable = imageView.drawable as Animatable
        if (animatable.isRunning) {
            animatable.stop()
        }
        animatable.start()
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun repeat(view: View) {
        val imageView = view as ImageView
        mAnimatedVectorDrawable = imageView.drawable as AnimatedVectorDrawable
        mAnimatedVectorDrawable?.registerAnimationCallback(object : Animatable2.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable) {
                imageView.post {
                    if (mAnimatedVectorDrawable?.isRunning == true) {
                        mAnimatedVectorDrawable?.stop()
                    }
                    mAnimatedVectorDrawable?.start()
                }
            }
        })
    }

}