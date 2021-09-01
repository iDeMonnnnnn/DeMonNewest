package com.demon.demonjetpack.module.views

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.demon.basemvvm.helper.ActivityHelper
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmFragment
import com.demon.basemvvm.utils.setOnClickThrottleFirst
import com.demon.demonjetpack.R
import com.demon.demonjetpack.databinding.FragmentDialogBinding
import com.demon.demonjetpack.module.main.MainActivity
import com.demon.demonjetpack.module.views.widget.TopDialog
import com.demon.demonjetpack.module.views.widget.TopWindow

/**
 * @author DeMon
 * Created on 2021/8/4.
 * E-mail 757454343@qq.com
 * Desc:
 */
class DialogFragment : MvvmFragment<FragmentDialogBinding, BaseViewModel>() {

    override fun init() {
        binding.run {

            btnTopWindow.setOnClickListener {
                val activity = ActivityHelper.getActivity(MainActivity::class.java)
                activity?.run {
                    TopWindow(this).showPopupWindow(root)
                }
            }

            btnDialog.setOnClickThrottleFirst {
                val activity = ActivityHelper.getActivity(MainActivity::class.java)
                activity?.run {
                    val dialog = Dialog(activity)
                    dialog.setContentView(R.layout.window_top)
                    dialog.setCancelable(false)
                    dialog.setCanceledOnTouchOutside(false)
                    val window = dialog.window
                    if (window != null) {
                        window.setGravity(Gravity.TOP)
                        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        val lp = WindowManager.LayoutParams()
                        lp.copyFrom(window.attributes)
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                        window.attributes = lp
                        window.setDimAmount(0f)
                    }
                    dialog.show()
                }

            }

            btnDialogFragment.setOnClickThrottleFirst {
                val activity = ActivityHelper.getActivity(MainActivity::class.java) as FragmentActivity
                val dialog = TopDialog()
                dialog.showAllowingState(activity.supportFragmentManager)
            }

            btnFloat.setOnClickThrottleFirst {
                requireActivity().startService(Intent(requireActivity(), FloatingService::class.java))
            }

            btnOne.setOnClickThrottleFirst {
                startActivity(Intent(requireActivity(), OnePxActivity::class.java))
            }
        }
    }
}