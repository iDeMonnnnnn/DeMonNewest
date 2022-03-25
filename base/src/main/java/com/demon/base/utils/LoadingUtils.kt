package com.demon.base.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.TextView
import com.demon.base.BuildConfig
import com.demon.base.R
import kotlinx.coroutines.launch

/**
 * @author DeMon
 * Created on 2022/3/25.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
object LoadingUtils {
    private var dialog: Dialog? = null

    @JvmStatic
    fun showSafety(context: Context?, isShowing: Boolean) {
        scopeMain.launch {
            show(context, isShowing)
        }
    }

    @JvmStatic
    fun show(context: Context?, isShowing: Boolean) {
        try {
            if (isShowing) {
                if (dialog == null && context != null) {
                    dialog = Dialog(context).also {
                        it.window?.let { window ->
                            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            window.setLayout(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            window.setGravity(Gravity.CENTER)
                        }
                        it.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        it.setContentView(R.layout.dialog_loading)
                        it.setCancelable(BuildConfig.DEBUG)
                    }
                }
                dialog?.show()
            } else {
                if (dialog != null) {
                    if (dialog?.isShowing == true) {
                        dialog?.dismiss()
                    }
                    dialog = null
                }
            }
        } catch (e: Exception) {
            Log.e(Tag, "showDialog: ${Log.getStackTraceString(e)}")
            dialog = null
        }
    }

    @JvmStatic
    fun show(context: Context?, isShowing: Boolean, content: String?) {
        try {
            var tvContent: TextView? = null
            if (isShowing) {
                if (dialog == null && context != null) {
                    dialog = Dialog(context).also {
                        it.window?.let { window ->
                            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            window.setLayout(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            window.setGravity(Gravity.CENTER)
                        }
                        it.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        it.setCancelable(BuildConfig.DEBUG)
                    }
                    val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
                    tvContent = view.findViewById<View>(R.id.tv_content) as TextView
                    dialog?.setContentView(view)
                }
                if (tvContent != null) {
                    if (!TextUtils.isEmpty(content)) {
                        tvContent.text = content
                    } else {
                        tvContent.visibility = View.GONE
                    }
                }
                dialog?.show()
            } else {
                if (dialog != null) {
                    if (dialog?.isShowing == true) {
                        dialog?.dismiss()
                    }
                    dialog = null
                }
            }
        } catch (e: Exception) {
            Log.e(Tag, "showDialog: ${Log.getStackTraceString(e)}")
            dialog = null
        }
    }
}