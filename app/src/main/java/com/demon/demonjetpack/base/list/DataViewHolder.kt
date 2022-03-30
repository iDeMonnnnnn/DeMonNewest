package com.demon.demonjetpack.base.list

import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.demon.base.utils.ext.inflateViewBinding
import kotlinx.android.extensions.LayoutContainer

/**
 * @author DeMon
 * Created on 2020/9/28.
 * E-mail 757454343@qq.com
 * Desc:
 */
open class DataViewHolder<VB : ViewBinding> : BaseViewHolder, LayoutContainer {

    val mContext = itemView.context

    var binding: VB

    constructor(binding: VB) : super(binding.root) {
        this.binding = binding
    }

    constructor(view: View) : super(view) {
        val parent = view as ViewGroup
        this.binding = inflateViewBinding(parent)
    }

    override val containerView: View?
        get() = itemView

}
