package com.demon.demonnewest.module.camera

import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.demonnewest.databinding.ActivityCameraxBinding

/**
 * @author DeMonnnnnn
 * date 2022/6/30
 * email liu_demon@qq.com
 * desc
 */
class CameraXActivity : MvvmActivity<ActivityCameraxBinding, BaseViewModel>() {
    override fun initData() {
        setToolbar("CameraX")
    }
}