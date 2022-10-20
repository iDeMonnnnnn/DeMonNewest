package com.demon.base.list

import android.content.Context
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.demon.base.R
import com.demon.base.utils.ext.inflateViewBinding

/**
 * @author DeMonnnnnn
 * date 2022/10/11
 * email liu_demon@qq.com
 * desc
 */
abstract class BaseVbQAdapter<T, VB : ViewBinding>(
    _datas: MutableList<T> = mutableListOf()
) : BaseQuickAdapter<T, DataVbHolder<VB>>(-1, _datas) {

    protected val TAG = javaClass.simpleName

    protected lateinit var mContext: Context

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): DataVbHolder<VB> {
        if (!this::mContext.isInitialized) {
            mContext = parent.context
        }
        val binding: VB = inflateViewBinding(parent, 1)
        return DataVbHolder(binding)
    }

    override fun convert(holder: DataVbHolder<VB>, item: T) {
        onBindItem(holder, holder.binding, item, holder.layoutPosition)
    }

    abstract fun onBindItem(holder: DataVbHolder<VB>, binding: VB, data: T, pos: Int)

    fun setData(newDatas: MutableList<T>) {
        if (newDatas.isEmpty()) {
            setEmptyView(R.layout.view_data_empty)
            setList(null)
        } else {
            setList(newDatas)
        }
    }
}