package com.demon.base.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.demon.base.utils.ext.inflateViewBinding
import com.demon.base.utils.ext.launchIO
import com.demon.base.utils.ext.scopeIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author DeMonnnnnn
 * date 2022/10/10
 * email liu_demon@qq.com
 * desc
 */
abstract class BaseVbAdapter<T, VB : ViewBinding>(
    _datas: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<DataVbHolder<VB>>() {

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

    open fun updateItem(data: T) {
        if (datas.contains(data)) {
            val position = datas.indexOf(data)
            notifyItemChanged(position)
        }
    }

    open fun addItem(data: T) {
        datas.add(data)
        notifyItemChanged(datas.size - 1)
    }

    open fun addAllItem(data: MutableList<T>) {
        datas.addAll(data)
        notifyItemChanged(datas.size - 1)
    }

    open fun removeItem(data: T) {
        if (datas.contains(data)) {
            val position = datas.indexOf(data)
            datas.remove(data)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, datas.size - position + 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataVbHolder<VB> {
        if (!this::mContext.isInitialized) {
            mContext = parent.context
        }
        val binding: VB = inflateViewBinding(parent, 1)
        return DataVbHolder(binding)
    }

    override fun onBindViewHolder(holder: DataVbHolder<VB>, position: Int) {
        onBindItem(holder, holder.binding, datas[position], position)
    }


    /**
     * 本质就是adapter的[onBindViewHolder]
     */
    abstract fun onBindItem(holder: DataVbHolder<VB>, binding: VB, data: T, position: Int)

    override fun getItemCount(): Int = datas.size

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
                scopeIO.launchIO {
                    val diff = update(old, update)
                    withContext(Dispatchers.Main) {
                        old.clear()
                        old.addAll(update)
                        diff.dispatchUpdatesTo(this@BaseVbAdapter)
                    }
                }
            }
        }
    }

    private fun update(oldItems: List<T>, update: List<T>): DiffUtil.DiffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = update.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = areContentsTheSame(oldItems[oldItemPosition], update[newItemPosition])

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = areItemsTheSame(oldItems[oldItemPosition], update[newItemPosition])
    })

    /**
     * 改为open重写，避免不需要DiffUtil的Adapter都要重写
     */
    protected open fun areItemsTheSame(oldItem: T, newItem: T): Boolean = false

    protected open fun areContentsTheSame(oldItem: T, newItem: T): Boolean = false
}