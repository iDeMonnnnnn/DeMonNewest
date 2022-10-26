package com.demon.base.mvvm

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
import com.demon.base.utils.ext.inflateViewBinding

/**
 * @author DeMonnnnnn
 * date 2022/9/23
 * email liu_demon@qq.com
 * desc  ViewBinding Fragment基类
 */
abstract class BaseVBFragment<VB : ViewBinding> : Fragment(), LifecycleEventObserver {
    protected open val TAG
        get() = this.javaClass.simpleName

    //是否已经懒加载
    private var isLazyLoad = false

    private var _binding: VB? = null

    // This property is only valid between onCreateView and onDestroyView.
    protected val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //add Fragment监听生命周期
        lifecycle.addObserver(this)
        _binding = inflateViewBinding(inflater, container)
        return _binding?.root
    }


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.i(TAG, "onStateChanged: $event,isLazyLoad=$isLazyLoad,isVisible=$isVisible")
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (!isLazyLoad) {
                    initLazyData()
                    isLazyLoad = true
                }
                this.onUserVisible(isVisible = true, isLazyLoad = true)
            }
            Lifecycle.Event.ON_PAUSE -> {
                onUserVisible(false, isLazyLoad)
            }
            else -> {

            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: ")
        //移除Fragment
        lifecycle.removeObserver(this)
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
            onUserVisible(isVisibleToUser, true)
        }
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


    protected fun bindingRun(block: VB.() -> Unit) {
        binding.run(block)
    }

}