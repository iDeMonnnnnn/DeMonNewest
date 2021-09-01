package com.demon.demonjetpack.module.views

import androidx.navigation.fragment.findNavController
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmFragment
import com.demon.demonjetpack.R
import com.demon.demonjetpack.databinding.FragmentViewBinding

/**
 * @author DeMon
 * Created on 2021/6/29.
 * E-mail 757454343@qq.com
 * Desc:
 */
class ViewsFragment : MvvmFragment<FragmentViewBinding, BaseViewModel>() {

    override fun init() {
        binding.btnViewBinding.setOnClickListener {
            findNavController().navigate(R.id.action_viewsFragment_to_viewBindingFragment)
        }

        binding.btnMotion.setOnClickListener {
            findNavController().navigate(ViewsFragmentDirections.actionViewsFragmentToMotionFragment())
        }

        binding.btnLighter.setOnClickListener {
            findNavController().navigate(ViewsFragmentDirections.actionViewsFragmentToLighterActivity())
        }

        binding.btnDoole.setOnClickListener {
            findNavController().navigate(ViewsFragmentDirections.actionViewsFragmentToDooleFragment())
        }

        binding.btnDialog.setOnClickListener {
            findNavController().navigate(ViewsFragmentDirections.actionViewsFragmentToDialogFragment())
        }
    }

}