package com.demon.base.mvvm

import android.os.Bundle
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
abstract class MvvmActivity<VB : ViewBinding, VM : BaseViewModel> : BaseVBActivity<VB>() {

    protected open val mViewModel: VM by lazy {
        ViewModelProvider(this)[getTClassIndex(1)]
    }


    override fun initFun() {
        vmRun {
            lifecycle.addObserver(this)
            errLiveData.observe(this@MvvmActivity) {
                doOnError(it)
            }
            loadingData.observe(this@MvvmActivity) {
                LoadingUtils.show(mContext, it)
            }
        }
        super.initFun()
    }


    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel)
    }

    protected fun vmRun(block: VM.() -> Unit) {
        mViewModel.run(block)
    }


    open fun doOnError(msg: String) {}

}
