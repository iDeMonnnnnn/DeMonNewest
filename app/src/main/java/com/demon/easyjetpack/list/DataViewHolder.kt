package com.demon.easyjetpack.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * @author DeMon
 * Created on 2020/9/28.
 * E-mail 757454343@qq.com
 * Desc:
 */
open class DataViewHolder constructor(@LayoutRes val layoutRes: Int, private val root: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(root.context).inflate(layoutRes, root, false)
), LayoutContainer {
    val mContext: Context = root.context

    override val containerView: View?
        get() = LayoutInflater.from(root.context).inflate(layoutRes, root, false)

}
