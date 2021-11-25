package com.demon.demonjetpack.module.paging

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.demon.basemvvm.utils.inflateViewBinding
import com.demon.demonjetpack.base.list.DataViewHolder

/**
 * @author DeMon
 * Created on 2020/9/28.
 * E-mail 757454343@qq.com
 * Desc:
 */
abstract class PagingAdapter<VB : ViewBinding, T : Any> constructor(
    private val swipe: SwipeRefreshLayout? = null, diffCallback: DiffUtil.ItemCallback<T> = DiffComparator()
) : PagingDataAdapter<T, DataViewHolder<VB>>(diffCallback) {

    init {
        addLoadStateListener {
            swipe?.isRefreshing = it.refresh is LoadState.Loading
        }
        swipe?.setOnRefreshListener {
            refresh()
        }
    }

    override fun onBindViewHolder(holder: DataViewHolder<VB>, position: Int) {
        val item = getItem(position)
        item?.run {
            onBind(holder, position, this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<VB> {
        val binding: VB = inflateViewBinding(parent)
        return DataViewHolder(binding)
    }


    abstract fun onBind(holder: DataViewHolder<VB>, position: Int, data: T)


    fun withFooter(): ConcatAdapter {
        return this.withLoadStateFooter(PagingLoadAdapter(object : RetryListener {
            override fun onRetry() {
                retry()
            }
        }))
    }

}