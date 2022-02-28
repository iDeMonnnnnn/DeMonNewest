package com.demon.demonjetpack.module.ara

import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.databinding.ActivityActResultBinding

class ActResultActivity : MvvmActivity<ActivityActResultBinding, BaseViewModel>() {

    override fun initData() {
        setToolbar("Activity Result API")
    }
}