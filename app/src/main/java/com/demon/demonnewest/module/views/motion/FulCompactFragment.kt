package com.demon.demonnewest.module.views.motion

import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmFragment
import com.demon.demonnewest.databinding.FragmentFullCompactBinding

/**
 * @author DeMon
 * Created on 2021/1/27.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class FulCompactFragment : MvvmFragment<FragmentFullCompactBinding, BaseViewModel>() {
    override fun initData() {
        binding.imagePlay.setOnClickListener {
            binding.motionLayout.transitionToStart()
        }

        binding.imageClear.setOnClickListener {
            binding.motionLayout.transitionToEnd()
        }
    }
}