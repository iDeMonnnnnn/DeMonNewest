package com.demon.demonjetpack.module.views.widget.motion

import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmFragment
import com.demon.demonjetpack.databinding.FragmentFullCompactBinding

/**
 * @author DeMon
 * Created on 2021/1/27.
 * E-mail 757454343@qq.com
 * Desc:
 */
class FulCompactFragment : MvvmFragment<FragmentFullCompactBinding, BaseViewModel>() {
    override fun init() {
        binding.imagePlay.setOnClickListener {
            binding.motionLayout.transitionToStart()
        }

        binding.imageClear.setOnClickListener {
            binding.motionLayout.transitionToEnd()
        }
    }
}