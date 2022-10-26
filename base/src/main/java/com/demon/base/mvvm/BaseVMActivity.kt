package com.demon.base.mvvm

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.demon.base.utils.LoadingUtils
import com.demon.base.utils.ext.getTClassIndex

/**
 * @author DeMonnnnnn
 * date 2022/10/26
 * email: liu_demon@qq.com
 * desc:
 */
abstract class BaseVMActivity<VM : BaseViewModel> : AppCompatActivity() {

    protected val TAG = this.javaClass.simpleName

    protected lateinit var mContext: Context

    protected open val mViewModel: VM by lazy {
        ViewModelProvider(this)[getTClassIndex()]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        vmRun {
            lifecycle.addObserver(this)
            errLiveData.observe(this@BaseVMActivity) {
                doOnError(it)
            }
            loadingData.observe(this@BaseVMActivity) {
                LoadingUtils.show(mContext, it)
            }
        }
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