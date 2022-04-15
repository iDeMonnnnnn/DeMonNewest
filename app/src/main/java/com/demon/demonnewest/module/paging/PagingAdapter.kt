package com.demon.demonnewest.module.paging

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.demon.base.utils.ext.inflateViewBinding
import com.demon.base.list.DataVbHolder

/**
 * @author DeMon
 * Created on 2020/9/28.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
abstract class PagingAdapter<VB : ViewBinding, T : Any> constructor(
    private val swipe: SwipeRefreshLayout? = null,
    diffCallback: DiffUtil.ItemCallback<T> = DiffComparator()
) : PagingDataAdapter<T, DataVbHolder<VB>>(diffCallback) {

    init {
        addLoadStateListener {
            swipe?.isRefreshing = it.refresh is LoadState.Loading
        }
        swipe?.setOnRefreshListener {
            refresh()
        }
    }

    override fun onBindViewHolder(holder: DataVbHolder<VB>, position: Int) {
        val item = getItem(position)
        item?.run {
            onBind(holder, position, this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataVbHolder<VB> {
        val binding: VB = inflateViewBinding(parent)
        return DataVbHolder(binding)
    }


    abstract fun onBind(holder: DataVbHolder<VB>, position: Int, data: T)


    fun withFooter(): ConcatAdapter {
        return this.withLoadStateFooter(PagingLoadAdapter { retry() })
    }

}