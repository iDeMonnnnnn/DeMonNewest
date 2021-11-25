package com.demon.basemvvm.mvvm

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import androidx.viewbinding.ViewBinding
import com.demon.basemvvm.helper.DialogHelp
import com.demon.basemvvm.utils.getTClass
import com.demon.basemvvm.utils.inflateViewBinding

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
abstract class MvvmFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {
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
        runCatching {
            lifecycle.addObserver(mViewModel)
            vmRun {
                lifecycle.addObserver(this)
                errLiveData.observe(viewLifecycleOwner) {
                    doOnError(it)
                }
                loadingData.observe(viewLifecycleOwner) {
                    DialogHelp.show(mContext, it)
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: ")
        lifecycle.removeObserver(mViewModel)
        isLoad = false
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
        if (isLoad) {
            onResumeRefresh()
        } else {
            initData()
            isLoad = true
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isLoad) {
            onResumeRefresh()
        }
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