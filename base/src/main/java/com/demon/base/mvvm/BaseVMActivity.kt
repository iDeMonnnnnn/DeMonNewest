package com.demon.base.mvvm

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.demon.base.utils.LoadingUtils
import com.demon.base.utils.ext.getTClassIndex

abstract class BaseVMActivity<VM : BaseViewModel> : AppCompatActivity() {
    protected val TAG = this.javaClass.simpleName
    protected lateinit var mContext: Context

    protected open val mViewModel: VM by lazy {
        providerVM()
    }

    /**
     * 解决子类继承后泛型位置改变导致的问题，
     * 如果子类泛型与父类泛型不完全一致，子类需要重新该方法
     */
    open fun providerVM(): VM = ViewModelProvider(this)[getTClassIndex()]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runCatching {
            mContext = this
            initContentView()
            mViewModel.run {
                lifecycle.addObserver(this)
                errLiveData.observe(this@BaseVMActivity) {
                    doOnError(it)
                }
                loadingData.observe(this@BaseVMActivity) {
                    LoadingUtils.show(mContext, it)
                }
            }
            initData()
        }.onFailure {
            it.printStackTrace()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel)
    }

    protected fun vmRun(block: VM.() -> Unit) {
        mViewModel.run(block)
    }

    protected abstract fun initContentView()

    protected abstract fun initData()


    open fun doOnError(msg: String) {}


}
