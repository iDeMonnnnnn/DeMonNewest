package com.demon.demonjetpack.module.views.lighter

import android.view.View
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.R
import com.demon.demonjetpack.databinding.ActivityLighterBinding
import com.demon.demonjetpack.databinding.LighterTipCommonBinding
import me.samlss.lighter.Lighter
import me.samlss.lighter.parameter.Direction
import me.samlss.lighter.parameter.LighterParameter
import me.samlss.lighter.parameter.MarginOffset
import me.samlss.lighter.shape.OvalShape

class LighterActivity : MvvmActivity<ActivityLighterBinding, BaseViewModel>() {

    private lateinit var lighter: Lighter
    override fun init() {
        intHighlight()
        lighter.show()
        binding.btn1.setOnClickListener {
            intHighlight()
            lighter.show()
        }
    }


    fun intHighlight() {
        lighter = Lighter.with(this)
            .setBackgroundColor(resources.getColor(R.color.transparent_60))
            .setAutoNext(false)
            .addHighlight(
                LighterParameter.Builder()
                    .setHighlightedViewId(R.id.btn1)
                    .setTipView(createCommonTipView("解决烦恼，第一步", "下一步"))
                    .setLighterShape(OvalShape())
                    .setTipViewRelativeDirection(Direction.RIGHT)
                    .setTipViewRelativeOffset(MarginOffset(100, 0, 0, 0))
                    .build()
            )
            .addHighlight(
                LighterParameter.Builder()
                    .setHighlightedViewId(R.id.btn2)
                    .setTipView(createCommonTipView("专业服务，第二步", "下一步"))
                    .setLighterShape(OvalShape())
                    .setTipViewRelativeDirection(Direction.RIGHT)
                    .setTipViewRelativeOffset(MarginOffset(50, 0, 100, 0))
                    .build(),
                LighterParameter.Builder()
                    .setHighlightedViewId(R.id.btn3)
                    .setTipView(View(this))
                    .setLighterShape(OvalShape())
                    .build()
            ).addHighlight(
                LighterParameter.Builder()
                    .setHighlightedViewId(R.id.btn4)
                    .setTipView(createCommonTipView("分享直播间，第三步", "知道了"))
                    .setLighterShape(OvalShape())
                    .setTipViewRelativeDirection(Direction.BOTTOM)
                    .setTipViewRelativeOffset(MarginOffset(0, 150, 100, 0))
                    .build()
            )
    }


    fun createCommonTipView(string1: String?, string2: String?): View {
        val binding = LighterTipCommonBinding.inflate(layoutInflater)
        binding.tvText.text = string1
        binding.ivButton.text = string2
        binding.ivButton.setOnClickListener {
            lighter.next()
        }
        return binding.root
    }


}