package com.demon.demonjetpack.module.views

import android.util.Log
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
            Log.i(TAG, "init: btnViewBinding")
            findNavController().navigate(R.id.action_viewsFragment_to_viewBindingFragment)
        }

        binding.btnMotion.setOnClickListener {
            Log.i(TAG, "init: btnMotion")
            findNavController().navigate(ViewsFragmentDirections.actionViewsFragmentToMotionFragment())
        }

        binding.btnLighter.setOnClickListener {
            Log.i(TAG, "init: btnLighter")
            findNavController().navigate(ViewsFragmentDirections.actionViewsFragmentToLighterActivity())
        }

        binding.btnDoole.setOnClickListener {
            Log.i(TAG, "init: btnLighter")
            findNavController().navigate(ViewsFragmentDirections.actionViewsFragmentToDooleFragment())
        }
    }

}