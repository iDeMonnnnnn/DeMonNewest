package com.demon.easyjetpack.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlinx.android.extensions.LayoutContainer

/**
 * @author DeMon
 * Created on 2020/9/28.
 * E-mail 757454343@qq.com
 * Desc:
 */
open class DataViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root), LayoutContainer {
    val mContext = binding.root.context

    override val containerView: View?
        get() = binding.root

}
