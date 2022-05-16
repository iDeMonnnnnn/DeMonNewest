package com.demon.demonnewest.module.views.animation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.demonnewest.databinding.ActivityAnimationBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author DeMon
 * Created on 2022/5/16.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class AnimationActivity : MvvmActivity<ActivityAnimationBinding, BaseViewModel>() {

    override fun initData() {
        val tabList = listOf("补间动画", "帧动画", "属性动画")
        val list = listOf<Fragment>(BetweenFragment(), FrameFragment(), ValueFragment())
        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = list.size

            override fun createFragment(position: Int): Fragment = list[position]
        }
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }
}