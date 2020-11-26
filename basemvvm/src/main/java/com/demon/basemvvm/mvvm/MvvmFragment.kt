package com.demon.basemvvm.mvvm

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.demon.basemvvm.helper.DialogHelp
import java.lang.reflect.ParameterizedType

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
abstract class MvvmFragment<VM : BaseViewModel> : Fragment() {
    private var isLoad = false
    protected lateinit var mContext: Context
    val mViewModel by lazy {
        val providerVMClass = (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        ViewModelProvider(this).get(providerVMClass)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(setupLayoutId(), container)
    }


    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run { mContext = this }
        runCatching {
            //lifecycle.addObserver(mViewModel)
            mViewModel.run {
                lifecycle.addObserver(this)
                errLiveData.observe(this@MvvmFragment) {
                    doOnErrLiveData(it)
                }
                loadingData.observe(this@MvvmFragment) {
                    DialogHelp.show(mContext, it)
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(mViewModel)
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