package com.demon.basemvvm.mvvm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import com.demon.basemvvm.helper.DialogHelp
import java.lang.reflect.ParameterizedType

abstract class MvvmVBActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {
    protected lateinit var mContext: Context

    protected val mViewModel by lazy {
        val providerVMClass = (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
        ViewModelProvider(this).get(providerVMClass)
    }

    protected lateinit var binding: VB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runCatching {
            val providerVBClass = (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
            binding = providerVBClass.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB
            setContentView(binding.root)
            mContext = this
            mViewModel.run {
                lifecycle.addObserver(this)
                errLiveData.observe(this@MvvmVBActivity) {
                    doOnErrLiveData()
                }
                loadingData.observe(this@MvvmVBActivity) {
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
