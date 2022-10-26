package com.demon.base.mvvm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.demon.base.utils.LoadingUtils
import com.demon.base.utils.ext.getTClassIndex
import com.demon.base.utils.ext.inflateViewBinding

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail idemon_liu@qq.com
 * Desc: MVVM架构+ViewBinding基类
 */
abstract class MvvmFragment<VB : ViewBinding, VM : BaseViewModel> : BaseVBFragment<VB>() {


    protected open val mViewModel: VM by lazy {
        ViewModelProvider(this)[getTClassIndex(1)]
    }


    open fun providerVM(): VM = ViewModelProvider(this)[getTClassIndex(1)]


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ")
        vmRun {
            //add ViewModel
            lifecycle.addObserver(this)
            errLiveData.observe(viewLifecycleOwner) {
                doOnError(it)
            }
            loadingData.observe(viewLifecycleOwner) {
                LoadingUtils.show(requireContext(), it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: ")

        //移除ViewModel
        lifecycle.removeObserver(mViewModel)
    }

    protected fun vmRun(block: VM.() -> Unit) {
        mViewModel.run(block)
    }

    open fun doOnError(msg: String) {}

}