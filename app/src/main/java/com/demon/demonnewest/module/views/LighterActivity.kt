package com.demon.demonnewest.module.views

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.base.utils.ext.getCompatColor
import com.demon.demonnewest.R
import com.demon.demonnewest.databinding.ActivityLighterBinding
import com.demon.demonnewest.databinding.LighterTip1Binding
import com.demon.demonnewest.databinding.LighterTip2Binding
import com.demon.demonnewest.databinding.LighterTip3Binding
import me.samlss.lighter.Lighter
import me.samlss.lighter.parameter.Direction
import me.samlss.lighter.parameter.LighterParameter
import me.samlss.lighter.parameter.MarginOffset
import me.samlss.lighter.shape.CircleShape
import me.samlss.lighter.shape.OvalShape
import me.samlss.lighter.shape.RectShape

/**
 * @author DeMon
 * Created on 2021/6/29.
 * E-mail 757454343@qq.com
 * Desc:
 * https://github.com/samlss/Lighter
 * 注:在Fragment中表现不佳
 */
class LighterActivity : MvvmActivity<ActivityLighterBinding, BaseViewModel>() {

    private lateinit var lighter: Lighter
    override fun initData() {
        setToolbar("Lighter")
        intHighlight()
        lighter.show()
        binding.btn1.setOnClickListener {
            intHighlight()
            lighter.show()
        }
    }


    private fun intHighlight() {
        lighter = Lighter.with(this)
            .setBackgroundColor(getCompatColor(R.color.transparent_60))
            .setAutoNext(false)
            .addHighlight(
                LighterParameter.Builder()
                    .setHighlightedViewId(R.id.btn1)
                    .setTipView(createCommonTipView1())
                    .setLighterShape(CircleShape())
                    .setTipViewRelativeDirection(Direction.TOP)
                    .setTipViewRelativeOffset(MarginOffset(0, 0, 0, -100))
                    .build()
            )
            .addHighlight(
                LighterParameter.Builder()
                    .setHighlightedViewId(R.id.btn2)
                    .setTipView(createCommonTipView2())
                    .setLighterShape(RectShape())
                    .setTipViewRelativeDirection(Direction.BOTTOM)
                    .setTipViewRelativeOffset(MarginOffset(0, 0, 0, 0))
                    .build(),
                LighterParameter.Builder()
                    .setHighlightedViewId(R.id.btn3)
                    .setTipView(View(this))
                    .setLighterShape(OvalShape())
                    .build()
            ).addHighlight(
                LighterParameter.Builder()
                    .setHighlightedViewId(R.id.btn4)
                    .setTipView(createCommonTipView3())
                    .setLighterShape(RectShape())
                    .setTipViewRelativeDirection(Direction.TOP)
                    .setTipViewRelativeOffset(MarginOffset(0, 150, 0, 0))
                    .build()
            )
    }

    private fun createCommonTipView1(): View {
        val binding = LighterTip1Binding.inflate(layoutInflater)
        val optionsScaleType = RequestOptions().override(Target.SIZE_ORIGINAL)
        Glide.with(this).load(R.drawable.gif1)
            .apply(optionsScaleType)
            .into(binding.ivClick)
        binding.ivButton.setOnClickListener {
            lighter.next()
        }
        return binding.root
    }


    private fun createCommonTipView2(): View {
        val binding = LighterTip2Binding.inflate(layoutInflater)
        val optionsScaleType = RequestOptions().override(Target.SIZE_ORIGINAL)
        Glide.with(this).load(R.drawable.gif2)
            .apply(optionsScaleType)
            .into(binding.ivClick)
        binding.ivButton.setOnClickListener {
            lighter.next()
        }
        return binding.root
    }


    private fun createCommonTipView3(): View {
        val binding = LighterTip3Binding.inflate(layoutInflater)
        val optionsScaleType = RequestOptions().override(Target.SIZE_ORIGINAL)
        Glide.with(this).load(R.drawable.gif3)
            .apply(optionsScaleType)
            .into(binding.ivClick)
        binding.ivButton.setOnClickListener {
            lighter.next()
        }
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        lighter.dismiss()
    }


}