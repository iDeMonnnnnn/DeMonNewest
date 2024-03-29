package com.demon.demonnewest.module.fragment

import com.demon.base.mvvm.BaseVBFragment
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmFragment
import com.demon.base.utils.LoadingUtils
import com.demon.base.utils.ext.launch
import com.demon.base.utils.ext.toActivity
import com.demon.base.utils.ext.setOnClickThrottleFirst
import com.demon.demonnewest.databinding.FragmentGoBinding
import com.demon.demonnewest.module.dp.AudioActivity
import kotlinx.coroutines.delay
import showRemindDialog

/**
 * @author DeMonnnnnn
 * date 2022/8/5
 * email liu_demon@qq.com
 * desc
 */
class GoFragment constructor(private val tips: String) : BaseVBFragment<FragmentGoBinding>() {

    override val TAG: String
        get() = tips

    override fun initLazyData() {

        bindingRun {
            tvText.text = tips

            btnGo.setOnClickThrottleFirst {
                toActivity<AudioActivity>()
            }

            btnDialog.setOnClickThrottleFirst {
                launch {
                    LoadingUtils.show(requireContext(), true)
                    delay(3000)
                    LoadingUtils.show(requireContext(), false)
                }
            }

            btnDialogFragment.setOnClickThrottleFirst {
                launch {
                    childFragmentManager.showRemindDialog(content = "遮挡弹窗", tag = TAG)
                }
            }
        }
    }
}