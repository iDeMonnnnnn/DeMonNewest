package com.demon.basemvvm.mvvm

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import com.demon.basemvvm.helper.DialogHelp
import com.demon.basemvvm.utils.getTClass
import com.demon.basemvvm.utils.inflateViewBinding

abstract class MvvmActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {
    protected val TAG = this.javaClass.simpleName
    protected lateinit var mContext: Context
    protected lateinit var binding: VB
    protected val mViewModel by lazy { ViewModelProvider(this).get(getTClass<VM>(1)) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runCatching {
            binding = inflateViewBinding(layoutInflater)
            setContentView(binding.root)
            mContext = this
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

    protected abstract fun init()

    open fun initViewModel() {}

    open fun doOnErrLiveData() {}


    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel)
    }
}
