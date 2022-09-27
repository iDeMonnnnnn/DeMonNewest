package com.demon.demonnewest.module.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.demon.base.mvvm.BaseVBFragment
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmFragment
import com.demon.base.utils.ext.getCompatDrawable
import com.demon.demonnewest.R
import com.demon.demonnewest.databinding.FragmentViewpage2Binding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author DeMonnnnnn
 * date 2022/8/5
 * email liu_demon@qq.com
 * desc
 */
class ViewPage2Fragment : BaseVBFragment<FragmentViewpage2Binding>() {


    private val oneFragment by lazy {
        GoFragment("ViewPage2Fragment-One")
    }

    private val twoFragment by lazy {
        GoFragment("ViewPage2Fragment-Two")
    }

    override fun initLazyData() {

        val list = mutableListOf(oneFragment, twoFragment)
        bindingRun {
            val adapter = object : FragmentStateAdapter(this@ViewPage2Fragment) {
                override fun getItemCount(): Int = list.size

                override fun createFragment(position: Int): Fragment = list[position]
            }
            viewPager2.adapter = adapter
            viewPager2.offscreenPageLimit = list.size
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = if (position == 0) {
                    "ONE"
                } else {
                    "TWO"
                }
                tab.icon = requireContext().getCompatDrawable(R.drawable.icon_logo)
            }.attach()
        }

    }
}