package com.demon.basemvvm.mvvm

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.demon.basemvvm.helper.DialogHelp
import com.demon.basemvvm.utils.getTClass

abstract class MvvmActivity<VM : BaseViewModel> : AppCompatActivity() {
    protected lateinit var mContext: Context

    protected val mViewModel by lazy { ViewModelProvider(this).get(getTClass<VM>()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setupLayoutId())
        mContext = this
        runCatching {
            mViewModel.run {
                lifecycle.addObserver(this)
                errLiveData.observe(this@MvvmActivity) {
                    doOnErrLiveData()
                }
                loadingData.observe(this@MvvmActivity) {
                    DialogHelp.show(mContext, it)
                }
            }
            init()
            initViewModel()
        }.onFailure {
            it.printStackTrace()
        }
    }


    protected abstract fun setupLayoutId(): Int

    protected abstract fun init()

    open fun initViewModel() {}

    open fun doOnErrLiveData() {}


    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel)
    }
}
