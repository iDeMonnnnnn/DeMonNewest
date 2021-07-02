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
            mViewModel.run {
                lifecycle.addObserver(this)
                errLiveData.observe(viewLifecycleOwner) {
                    doOnErrLiveData(it)
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


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
    }

    
    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }
    

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }


    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach: ")
    }
}