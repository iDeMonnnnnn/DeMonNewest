package com.demon.easyjetpack.module.fragment

import android.util.Log
import kotlinx.android.synthetic.main.fragment_tab.*
import androidx.lifecycle.observe
import com.demon.basemvvm.mvvm.MvvmFragment
import com.demon.easyjetpack.R

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
class TabFragment constructor(var city: String) : MvvmFragment<FragmentViewModel>() {

    override fun setupLayoutId(): Int = R.layout.fragment_tab

    override fun init() {
        tvCity.text = city
    }

    override fun initViewModel() {
        mViewModel.run {
            getWeather(city)
            weatherData.observe(this@TabFragment) {
                text.text = it
            }
        }
    }

    override fun onResumeRefresh() {
        super.onResumeRefresh()
        Log.i(TAG, "onResumeRefresh $city")
    }

}