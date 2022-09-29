package com.demon.base.mvvm

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.core.view.setPadding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.demon.base.utils.LoadingUtils
import com.demon.base.utils.ext.*
import kotlinx.coroutines.flow.*

/**
 * @author DeMonnnnnn
 * date 2022/9/23
 * email liu_demon@qq.com
 * desc
 */
abstract class BaseDialogFragment<VB : ViewBinding, VM : BaseViewModel> : DialogFragment() {

    /**
     * include的布局需要自己加上id，如果包含merge的话，需要单独对对应的布局进行bind，
     * 比如merge_toolbar.xml
     * ```kotlin
     *  protected lateinit var mergeToolbar: MergeToolbarBinding
     *  mergeToolbar = MergeToolbarBinding.bind(_binding.root)
     * ```
     */
    protected val TAG = this.javaClass.simpleName

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    protected val mViewModel by lazy {
        ViewModelProvider(this)[getTClassIndex<VM>(1)]
    }

    protected open val layoutWidth
        get() = WindowManager.LayoutParams.WRAP_CONTENT

    protected open val layoutHeight
        get() = WindowManager.LayoutParams.WRAP_CONTENT

    protected open val padding
        get() = 0

    protected open val backgroundDrawable
        get() = ColorDrawable(Color.TRANSPARENT)

    protected open val gravity
        get() = Gravity.CENTER

    protected open val windowFeature
        get() = Window.FEATURE_NO_TITLE

    protected open val outSideCancelable
        get() = true

    protected open val cancelable
        get() = true

    protected open val keyBackCancelable
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.requestWindowFeature(windowFeature)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        dialog?.run {
            isCancelable = cancelable
            setCanceledOnTouchOutside(outSideCancelable)
            setCancelable(cancelable)
            setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    keyBackCancelable
                } else {
                    false
                }
            }
        }
        setDialogWindow()
        _binding = inflateViewBinding(inflater, container)
        return _binding?.root
    }

    protected open fun setDialogWindow() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.run {
            if (padding != 0) { //设置边距
                decorView.setPadding(padding.intDp)
            }
            setGravity(gravity)
            setBackgroundDrawable(backgroundDrawable) //设置宽高
            setLayout(layoutWidth, layoutHeight)
        }
        runCatching {
            vmRun {
                //add ViewModel
                lifecycle.addObserver(this)
                errLiveData.observe(viewLifecycleOwner) {
                    doOnError(it)
                }
                loadingData.observe(viewLifecycleOwner) {
                    LoadingUtils.show(requireContext(), it)
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
        setupData()
    }

    protected abstract fun setupData()


    override fun onDestroyView() {
        _binding = null
        //移除ViewModel
        lifecycle.removeObserver(mViewModel)
        super.onDestroyView()
    }


    protected fun vmRun(block: VM.() -> Unit) {
        mViewModel.run(block)
    }

    protected fun bindingRun(block: VB.() -> Unit) {
        binding.run(block)
    }

    open fun doOnError(msg: String) {}


    /**
     * 关闭弹窗的时候调用dismissAllowingStateLoss
     */
    open fun showAllowingState(manager: FragmentManager) {
        showAllowingState(manager, tag)
    }

    /**
     * 关闭弹窗的时候调用dismissAllowingStateLoss
     */
    open fun showAllowingState(manager: FragmentManager, tagStr: String? = null) {
        manager.beginTransaction().add(this, tagStr ?: tag).commitAllowingStateLoss()
    }
}