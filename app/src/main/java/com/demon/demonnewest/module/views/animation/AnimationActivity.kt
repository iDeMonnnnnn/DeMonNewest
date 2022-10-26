package com.demon.demonnewest.module.views.animation

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.demon.base.mvvm.BaseVBActivity
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
class AnimationActivity : BaseVBActivity<ActivityAnimationBinding>() {

    override fun setupData() {
        setToolbar("原生动画")
        val tabList = listOf("补间动画", "帧动画", "属性动画")
        val list = listOf<Fragment>(BetweenFragment(), FrameFragment(), ValueFragment())
        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = list.size

            override fun createFragment(position: Int): Fragment = list[position]

            override fun onViewDetachedFromWindow(holder: FragmentViewHolder) {
                super.onViewDetachedFromWindow(holder)
                Log.i(TAG, "onViewDetachedFromWindow: ${holder.adapterPosition}")
            }

            override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
                super.onDetachedFromRecyclerView(recyclerView)
                Log.i(TAG, "onDetachedFromRecyclerView: ")
            }
        }
        binding.viewPager2.adapter = adapter
        binding.viewPager2.offscreenPageLimit = list.size
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }
}