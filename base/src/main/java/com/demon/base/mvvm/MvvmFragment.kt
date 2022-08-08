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

    protected open val TAG
        get() = this.javaClass.simpleName

    //是否已经懒加载
    private var isLazyLoad = false
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
            isLazyLoad = false
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
        Log.i(TAG, "onStateChanged: $event,isLazyLoad=$isLazyLoad,isVisible=$isVisible")
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (!isLazyLoad) {
                    initLazyData()
                    isLazyLoad = true
                    onUserVisible(true, isLazyLoad)
                } else {
                    onUserVisible(true, isLazyLoad)
                }
            }
            Lifecycle.Event.ON_PAUSE -> {
                onUserVisible(false, isLazyLoad)
            }
        }
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.i(TAG, "onHiddenChanged: hidden=$hidden,isLazyLoad=$isLazyLoad,isVisible=$isVisible")
        onUserVisible(!hidden, isLazyLoad)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.i(TAG, "setUserVisibleHint: isVisibleToUser=$isVisibleToUser,isLazyLoad=$isLazyLoad,isVisible=$isVisible")
        if (isLazyLoad) {
            onUserVisible(isVisibleToUser, isLazyLoad)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: $isVisible")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }


    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: ")
        //移除Fragment
        lifecycle.removeObserver(this)
        //移除ViewModel
        lifecycle.removeObserver(mViewModel)
        isLazyLoad = false
        _binding = null
    }

    protected fun vmRun(block: VM.() -> Unit) {
        mViewModel.run(block)
    }

    protected fun bindingRun(block: VB.() -> Unit) {
        binding.run(block)
    }

    /**
     * 懒加载，Fragment生命周期内只会触发一次，
     * 适用于初始化，不适用接口刷新，也不适用于协程等与生命周期有关的东西
     */
    protected abstract fun initLazyData()

    /**
     * Fragment可见性：
     * 1. 如果是add/replace就要通onResume/onPause判断isVisible()
     * 2. 如果是show/hide就要通过onHiddenChanged判断hidden
     * 3. 如果是ViewPager中的Fragment
     *     3.1 BEHAVIOR_SET_USER_VISIBLE_HINT会触发setUserVisibleHint，判断isVisibleToUser
     *     3.2 BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT不会触发[setUserVisibleHint]，走[onResume]/[onPause]
     * 4. 如果是ViewPager2中的Fragment就要通过走[onResume]/[onPause]
     *
     * 补充：
     * 1. Fragment再Activity的[onCreate]中直接加载时（一般来说是第一个默认的），onResume中isVisible=false，因此需要增加一个变量临时判断
     * 2. [onResume]/[onPause]可以实现LifecycleEventObserver接口，重写onStateChanged判断
     *
     * 使用时机：
     * 1.返回fragment时,isVisible=true,isLazyLoad=true刷新
     * 2.fragment可见不可见曝光埋点
     *
     * @param isVisible 是否可见
     * @param isLazyLoad 是否已经懒加载过
     */
    open fun onUserVisible(isVisible: Boolean, isLazyLoad: Boolean) {
        Log.i(TAG, "onUserVisible: isVisible=$isVisible,isFirstLoad=$isLazyLoad")
        if (isVisible && isLazyLoad) {
            onReVisibleRefresh()
        }
    }

    /**
     * fragment重新可见时，刷新数据可重写
     */
    open fun onReVisibleRefresh() {}

    open fun doOnError(msg: String) {}

}