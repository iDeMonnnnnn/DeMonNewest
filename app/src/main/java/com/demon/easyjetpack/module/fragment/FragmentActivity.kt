package com.demon.easyjetpack.module.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.easyjetpack.R
import com.demon.easyjetpack.data.RouterConst
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_fragment.*

@Route(path = RouterConst.ACT_FRAGMENT)
class FragmentActivity : MvvmActivity<BaseViewModel>() {

    override fun setupLayoutId(): Int = R.layout.activity_fragment

    override fun init() {

        val list = arrayListOf("广州","北京","上海")

        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 3

            override fun createFragment(position: Int): Fragment = TabFragment(list[position])
        }
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = list[position]
        }.attach()
    }

    override fun initViewModel() {
    }

}
