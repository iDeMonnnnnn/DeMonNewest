package com.demon.demonjetpack.module.views.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.demon.basemvvm.utils.setOnClickThrottleFirst
import com.demon.demonjetpack.base.ext.toast
import com.demon.demonjetpack.databinding.WindowTopBinding

/**
 * @author DeMon
 * Created on 2021/8/31.
 * E-mail 757454343@qq.com
 * Desc:
 */
class TopDialog : DialogFragment() {

    private var binding: WindowTopBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.run {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            isCancelable = false
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }
        binding = WindowTopBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.run {
            setGravity(Gravity.TOP)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            setDimAmount(0f)
        }
        binding?.run {
            btnClick.setOnClickThrottleFirst {
                "点击".toast()
                dismissAllowingStateLoss()
            }
        }
    }


    /**
     * 关闭弹窗的时候调用dismissAllowingStateLoss
     */
    fun showAllowingState(manager: FragmentManager) {
        manager.beginTransaction().add(this, tag).commitAllowingStateLoss()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}