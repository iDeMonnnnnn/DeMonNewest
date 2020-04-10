package com.demon.basemvvm.mvvm

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import java.lang.reflect.ParameterizedType
import javax.inject.Inject
import androidx.lifecycle.observe

abstract class MvvmActivity<VM : BaseViewModel> : DaggerAppCompatActivity() {
    protected lateinit var mContext: Context
    protected val TAG = javaClass.simpleName
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var mViewModel: VM
    private lateinit var providerVMClass: Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setupLayoutId())
        mContext = this
        runCatching {
            providerVMClass = (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
            providerVMClass.let {
                mViewModel = ViewModelProvider(this, viewModelFactory)[it]
                mViewModel.let(lifecycle::addObserver)
                mViewModel.mContext = mContext
            }
        }.onFailure {
            it.printStackTrace()
        }

        init()
        initViewModel()
        mViewModel.run {
            errLiveData.observe(this@MvvmActivity) {
                doOnErrLiveData()
                ///it.toast(mContext)
            }
        }
    }


    protected abstract fun setupLayoutId(): Int

    protected abstract fun init()

    open fun initViewModel(){}

    open fun doOnErrLiveData(){}

}
