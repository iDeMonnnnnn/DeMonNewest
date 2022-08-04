package com.demon.demonnewest.module.fragment

import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.demonnewest.databinding.ActivityFragsBinding

/**
 * @author DeMonnnnnn
 * date 2022/8/4
 * email liu_demon@qq.com
 * desc
 */
class FragsActivity : MvvmActivity<ActivityFragsBinding, BaseViewModel>() {
    override fun initData() {
        setToolbar("Fragment可见性")
    }
}