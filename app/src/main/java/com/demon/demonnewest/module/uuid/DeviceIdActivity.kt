package com.demon.demonnewest.module.uuid

import android.Manifest
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.base.utils.SystemUtils
import com.demon.base.utils.ext.toast
import com.demon.demonnewest.databinding.ActivityDeviceIdBinding
import com.permissionx.guolindev.PermissionX

/**
 * @author DeMonnnnnn
 * date 2022/9/7
 * email liu_demon@qq.com
 * desc
 */
class DeviceIdActivity : MvvmActivity<ActivityDeviceIdBinding, BaseViewModel>() {

    override fun initData() {
        getIDs()
        binding.btn.setOnClickListener {
            PermissionX.init(this)
                .permissions(Manifest.permission.READ_PHONE_STATE)
                .request { allGranted, _, _ ->
                    if (!allGranted) {
                        "手机权限获取失败~".toast()
                    } else {
                        getIDs()
                    }
                }
        }
    }


    private fun getIDs() {

        val sb = StringBuilder()
        sb.append("AndroidID=${SystemUtils.getAndroidId()}\n")
            .append("DeviceId=${SystemUtils.getDeviceId()}\n")
            .append("MAC=${SystemUtils.getMac()}")

        binding.tv.text = sb.toString()
    }
}