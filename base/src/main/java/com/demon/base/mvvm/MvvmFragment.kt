package com.demon.base.mvvm

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider

import androidx.viewbinding.ViewBinding
import com.demon.base.utils.LoadingUtils
import com.demon.base.utils.ext.getTClass
import com.demon.base.utils.ext.inflateViewBinding

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
abstract class MvvmFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment(), LifecycleEventObserver {
    protected val TAG = this.javaClass.simpleName
    private var isLoad = false
    protected lateinit var mContext: Context
    private var _binding: VB? = null

    // This property is only valid between onCreateView and onDestroyView.
    protected val binding get() = _binding!!
    protected val mViewModel by lazy {
        ViewModelProvider(this).get(getTClass<VM>(1))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(TAG, "onCreateView: ")
        runCatching {
            isLoad = false
            _binding = inflateViewBinding<VB>(inflater, container)
            return _binding?.root
        }.onFailure {
            it.printStackTrace()
        }
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ")
        activity?.run { mContext = this }
        //add Fragment监听生命周期
        lifecycle.addObserver(this)
        runCatching {
            vmRun {
                //add ViewModel
                lifecycle.addObserver(this)
                errLiveData.observe(viewLifecycleOwner) {
                    doOnError(it)
                }
                loadingData.observe(viewLifecycleOwner) {
                    LoadingUtils.show(mContext, it)
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.i(TAG, "onStateChanged: $event,isLoad=$isLoad")
        if (event == Lifecycle.Event.ON_RESUME) {
            if (isLoad) {
                onResumeRefresh()
            } else {
                initData()
                isLoad = true
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: ")
        //移除Fragment
        lifecycle.removeObserver(this)
        //移除ViewModel
        lifecycle.removeObserver(mViewModel)
        isLoad = false
        _binding = null
    }

    protected fun vmRun(block: VM.() -> Unit) {
        mViewModel.run(block)
    }

    protected fun bindingRun(block: VB.() -> Unit) {
        binding.run(block)
    }

    protected abstract fun initData()


    /**
     * 返回fragment刷新数据时重写
     */
    open fun onResumeRefresh() {}

    open fun doOnError(msg: String) {}

}