package com.demon.demonnewest.module.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.demon.base.mvvm.BaseVBFragment
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmFragment
import com.demon.demonnewest.databinding.FragmentViewpageBinding

/**
 * @author DeMonnnnnn
 * date 2022/8/5
 * email liu_demon@qq.com
 * desc
 */
class ViewPageFragment : BaseVBFragment<FragmentViewpageBinding>() {

    private val oneFragment by lazy {
        GoFragment("ViewPageFragment-One")
    }

    private val twoFragment by lazy {
        GoFragment("ViewPageFragment-Two")
    }

    override fun initLazyData() {
        val list = mutableListOf(oneFragment, twoFragment)
        bindingRun {

            /**
             * 1. BEHAVIOR_SET_USER_VISIBLE_HINT会触发setUserVisibleHint
             * 2. BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT不会触发[setUserVisibleHint]，走[onResume]/[onPause]
             */
            viewPager.adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
                override fun getCount(): Int = list.size

                override fun getItem(position: Int): Fragment = list[position]

                override fun getPageTitle(position: Int): CharSequence {
                    return if (position == 0) {
                        "ONE"
                    } else {
                        "TWO"
                    }
                }
            }

            tabLayout.setupWithViewPager(viewPager)

        }

    }
}