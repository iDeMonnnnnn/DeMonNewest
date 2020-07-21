package com.demon.basemvvm.mvvm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.demon.basemvvm.helper.DialogHelp
import dagger.android.support.DaggerFragment
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
abstract class MvvmFragment<VM : BaseViewModel> : DaggerFragment() {
    protected val TAG = javaClass.simpleName
    private var isLoad = false
    protected lateinit var mContext: Context

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var mViewModel: VM
    private lateinit var providerVMClass: Class<VM>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(setupLayoutId(), container)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run { mContext = this }
        runCatching {
            providerVMClass = (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
            providerVMClass.let { it ->
                mViewModel = ViewModelProvider(this, viewModelFactory)[it]
                mViewModel.let(lifecycle::addObserver)
                mViewModel.run {
                    errLiveData.observe(this@MvvmFragment) {
                        doOnErrLiveData(it)
                    }
                    loadingData.observe(this@MvvmFragment) {
                        DialogHelp.show(mContext, it)
                    }
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
    }


    override fun onResume() {
        super.onResume()
        if (!isLoad) {
            init()
            initViewModel()
            isLoad = true
        } else {
            onResumeRefresh()
        }
    }


    protected abstract fun setupLayoutId(): Int

    protected abstract fun init()

    open fun initViewModel() {}

    /**
     * 返回fragment刷新数据时重写
     */
    open fun onResumeRefresh() {}

    open fun doOnErrLiveData(msg: String) {}
}