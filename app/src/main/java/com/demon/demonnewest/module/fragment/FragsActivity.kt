package com.demon.demonnewest.module.fragment

import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.base.utils.LoadingUtils
import com.demon.base.utils.ext.launch
import com.demon.base.utils.ext.showFragment
import com.demon.base.utils.ext.setOnClickThrottleFirst
import com.demon.demonnewest.R
import com.demon.demonnewest.databinding.ActivityFragsBinding
import kotlinx.coroutines.delay
import showRemindDialog


/**
 * @author DeMonnnnnn
 * date 2022/8/4
 * email liu_demon@qq.com
 * desc
 */
class FragsActivity : MvvmActivity<ActivityFragsBinding, BaseViewModel>() {

    private val showFragment by lazy {
        ShowFragment()
    }
    private val replaceFragment by lazy {
        ReplaceFragment()
    }
    private val viewPageFragment by lazy {
        ViewPageFragment()
    }
    private val viewPage2Fragment by lazy {
        ViewPage2Fragment()
    }

    override fun initData() {
        setToolbar("Fragment可见性")

        bindingRun {
            btn1.setOnClickThrottleFirst {
                showFragment(R.id.frameLayout, showFragment)
            }
            btn2.setOnClickThrottleFirst {
                showFragment(R.id.frameLayout, replaceFragment)
            }
            btn3.setOnClickThrottleFirst {
                showFragment(R.id.frameLayout, viewPageFragment)
            }
            btn4.setOnClickThrottleFirst {
                showFragment(R.id.frameLayout, viewPage2Fragment)
            }

            btnDialog.setOnClickThrottleFirst {
                launch {
                    LoadingUtils.show(mContext, true)
                    delay(3000)
                    LoadingUtils.show(mContext, false)
                }

            }

            btnDialogFragment.setOnClickThrottleFirst {
                launch {
                    supportFragmentManager.showRemindDialog(content = "遮挡弹窗", tag = TAG)
                }
            }
        }

    }

}