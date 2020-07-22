package com.demon.basemvvm.utils

import android.app.ProgressDialog
import android.content.Context

/**
 * @author DeMon
 * Created on 2020/7/20.
 * E-mail 757454343@qq.com
 * Desc:
 */
object DialogHelp {

    private var dialog: ProgressDialog? = null

    fun show(mContext: Context, boolean: Boolean) {
        if (boolean) {
            if (dialog == null) {
                dialog = ProgressDialog(mContext)
            }
            dialog?.show()
        } else {
            if (dialog != null && dialog?.isShowing == true) {
                dialog?.dismiss()
            }
            dialog = null
        }
    }
}