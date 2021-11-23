package com.demon.demonjetpack.list

import android.view.View
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer

/**
 * @author DeMon
 * Created on 2020/9/28.
 * E-mail 757454343@qq.com
 * Desc:
 */
open class DataViewHolder<VB : ViewBinding> constructor(val binding: VB) : BaseViewHolder(binding.root), LayoutContainer {

    val mContext = itemView.context


    override val containerView: View?
        get() = itemView

}
