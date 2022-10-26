package com.demon.base.mvvm

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.demon.base.utils.LoadingUtils
import com.demon.base.utils.ext.getTClassIndex

/**
 * @author DeMonnnnnn
 * date 2022/9/23
 * email liu_demon@qq.com
 * desc MVVM架构+ViewBinding基类
 */
abstract class MvvmDialogFragment<VB : ViewBinding, VM : BaseViewModel> : BaseVBDialogFragment<VB>() {

    protected val mViewModel by lazy {
        ViewModelProvider(this)[getTClassIndex<VM>(1)]
    }

    override fun initFun() {
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
        super.initFun()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //移除ViewModel
        lifecycle.removeObserver(mViewModel)
    }


    protected fun vmRun(block: VM.() -> Unit) {
        mViewModel.run(block)
    }

    open fun doOnError(msg: String) {}

}