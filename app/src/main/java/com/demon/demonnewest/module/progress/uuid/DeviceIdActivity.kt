package com.demon.demonnewest.module.progress.uuid

import android.Manifest
import com.blankj.utilcode.util.DeviceUtils
import com.demon.base.mvvm.BaseVBActivity
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.base.utils.SystemUtils
import com.demon.base.utils.ext.setOnClickThrottleFirst
import com.demon.base.utils.ext.toast
import com.demon.demonnewest.databinding.ActivityDeviceIdBinding
import com.permissionx.guolindev.PermissionX

/**
 * @author DeMonnnnnn
 * date 2022/9/7
 * email liu_demon@qq.com
 * desc
 */
class DeviceIdActivity : BaseVBActivity<ActivityDeviceIdBinding>() {

    override fun setupData() {
        getIDs()
        binding.btn.setOnClickThrottleFirst {
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
        sb.append("AndroidID=${DeviceUtils.getAndroidID()}\n")
            .append("DeviceId=${DeviceUtils.getUniqueDeviceId()}\n")
            .append("IMEI=${SystemUtils.getDeviceId()}\n")
            .append("MAC=${DeviceUtils.getMacAddress()}")

        binding.tv.text = sb.toString()
    }
}