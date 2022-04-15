package com.demon.base.list

import android.content.Context
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.binder.BaseItemBinder
import com.demon.base.utils.ext.inflateViewBinding

/**
 * @author DeMon
 * Created on 2022/4/14.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
abstract class BaseVbItemBinder<T, VB : ViewBinding> : BaseItemBinder<T, DataVbHolder<VB>>() {
    protected lateinit var mContext: Context

    override fun convert(holder: DataVbHolder<VB>, data: T) {
        convertItem(holder, holder.binding, data, holder.layoutPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataVbHolder<VB> {
        mContext = parent.context
        val binding: VB = inflateViewBinding(parent, 1)
        return DataVbHolder(binding)
    }

    abstract fun convertItem(holder: DataVbHolder<VB>, binding: VB, data: T, pos: Int)
}