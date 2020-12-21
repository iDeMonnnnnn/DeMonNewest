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
import androidx.viewbinding.ViewBinding
import com.demon.basemvvm.helper.DialogHelp
import com.demon.basemvvm.utils.getTClass

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
abstract class MvvmVBFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {
    private var isLoad = false
    protected lateinit var mContext: Context
    private var _binding: VB? = null

    // This property is only valid between onCreateView and onDestroyView.
    protected val binding get() = _binding!!
    protected val mViewModel by lazy {
        ViewModelProvider(this).get(getTClass<VM>())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        runCatching {
            _binding = getTClass<VB>().getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
                .invoke(null, inflater, container, false) as VB
            return _binding?.root
        }.onFailure {
            it.printStackTrace()
        }
        return null
    }


    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run { mContext = this }
        runCatching {
            lifecycle.addObserver(mViewModel)
            mViewModel.run {
                lifecycle.addObserver(this)
                errLiveData.observe(this@MvvmVBFragment) {
                    doOnErrLiveData(it)
                }
                loadingData.observe(this@MvvmVBFragment) {
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
        _binding = null
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


    protected abstract fun init()

    open fun initViewModel() {}

    /**
     * 返回fragment刷新数据时重写
     */
    open fun onResumeRefresh() {}

    open fun doOnErrLiveData(msg: String) {}
}