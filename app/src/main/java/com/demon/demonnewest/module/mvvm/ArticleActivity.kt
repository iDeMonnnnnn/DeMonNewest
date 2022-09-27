package com.demon.demonnewest.module.mvvm

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.base.mvvm.BaseVBActivity
import com.demon.base.mvvm.BaseVMActivity
import com.demon.base.utils.ext.extraAct
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.demonnewest.base.data.RouterConst
import com.demon.demonnewest.databinding.ActivityArticleBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterConst.ACT_ARTICLE)
@AndroidEntryPoint
class ArticleActivity : BaseVBActivity<ActivityArticleBinding>() {

    var list: ArrayList<String> by extraAct("params", arrayListOf())

    override fun initData() {
        setToolbar("MVVM")
        list = arrayListOf("鸿洋", "郭霖")

        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = list.size

            override fun createFragment(position: Int): Fragment = ArticleFragment(list[position])
        }
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = list[position]
        }.attach()
    }

}
