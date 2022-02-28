package com.demon.demonjetpack.base.list

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.demon.basemvvm.utils.inflateViewBinding
import com.demon.basemvvm.utils.launchIO
import com.demon.demonjetpack.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext

/**
 * @author DeMon
 * Created on 2021/11/22.
 * E-mail 757454343@qq.com
 * Desc:
 */
abstract class BaseAdapter<T, VB : ViewBinding>(
    _datas: MutableList<T> = mutableListOf()
) : BaseQuickAdapter<T, DataViewHolder<VB>>(-1, _datas) {

    protected val TAG = javaClass.simpleName
    protected lateinit var mContext: Context

    open var datas = _datas
        /**
         * 使用[DiffUtil.DiffResult]做了一定的优化处理，但需要你在你的adapter里面，实现
         * 属于你自己的[areItemsTheSame]和[areContentsTheSame]方法，才能实现优化
         */
        set(update) {
            replace(field, update)
        }


    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<VB> {
        mContext = parent.context
        val binding: VB = inflateViewBinding(parent, 1)
        return DataViewHolder(binding)
    }


    private fun replace(old: MutableList<T>, update: MutableList<T>) {
        when {
            old.isEmpty() -> {
                old.addAll(update)
                notifyDataSetChanged()
            }
            update.isEmpty() -> {
                old.clear()
                notifyDataSetChanged()
            }
            else -> {
                GlobalScope.launchIO {
                    val diff = update(old, update)
                    withContext(Dispatchers.Main) {
                        diff.dispatchUpdatesTo(this@BaseAdapter)
                    }
                }
            }
        }
    }

    fun setData(newDatas: MutableList<T>) {
        if (newDatas.isNullOrEmpty()) {
            setEmptyView(R.layout.view_data_empty)
            setList(null)
        } else {
            datas = newDatas
        }
    }

    private fun update(oldItems: List<T>, update: List<T>): DiffUtil.DiffResult =
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = oldItems.size

            override fun getNewListSize() = update.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areContentsTheSame(oldItems[oldItemPosition], update[newItemPosition])

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areItemsTheSame(oldItems[oldItemPosition], update[newItemPosition])
        })

    /**
     * 改为open重写，避免不需要DiffUtil的Adapter都要重写
     */
    protected open fun areItemsTheSame(oldItem: T, newItem: T): Boolean = false

    protected open fun areContentsTheSame(oldItem: T, newItem: T): Boolean = false

}