package com.demon.demonjetpack.module.views.widget

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.demon.demonjetpack.base.ext.toast
import com.demon.demonjetpack.databinding.WindowTopBinding

/**
 * @author DeMon
 * Created on 2021/8/31.
 * E-mail 757454343@qq.com
 * Desc:
 */
class TopWindow constructor(val activity: Activity) : PopupWindow() {

    val binding: WindowTopBinding = WindowTopBinding.inflate(LayoutInflater.from(activity))

    init {
        contentView = binding.root

        // 设置SelectPicPopupWindow弹出窗体的宽
        this.width = RelativeLayout.LayoutParams.MATCH_PARENT
        // 设置SelectPicPopupWindow弹出窗体的高
        this.height = RelativeLayout.LayoutParams.WRAP_CONTENT
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.isFocusable = false
        this.isOutsideTouchable = false

        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //setBackgroundDrawable(null)

        binding.btnClick.setOnClickListener {
            "点击".toast()
            dismiss()
        }
    }


    /**
     * 显示popupWindow
     *
     * @param parent
     */
    fun showPopupWindow(parent: View) {
        if (!this.isShowing) {
            showAtLocation(parent, Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
        } else {
            dismiss()
        }
    }

}