package com.demon.easyjetpack.module.paging

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.demon.basemvvm.utils.Tag
import com.demon.easyjetpack.R
import com.demon.easyjetpack.base.ext.toast
import com.demon.easyjetpack.list.DataViewHolder
import kotlinx.android.synthetic.main.load_state.view.*

/**
 * @author DeMon
 * Created on 2020/9/29.
 * E-mail 757454343@qq.com
 * Desc:
 */

interface RetryListener {
    fun onRetry()
}

class PagingLoadAdapter constructor(private val retry: RetryListener? = null) : LoadStateAdapter<DataViewHolder>() {

    override fun onBindViewHolder(holder: DataViewHolder, loadState: LoadState) {
        holder.itemView.run {
            when (loadState) {
                is LoadState.Loading -> {
                    loading_view.visibility = View.VISIBLE
                    error_text.visibility = View.GONE
                    nomore_text.visibility = View.GONE
                    setOnClickListener(null)
                }
                is LoadState.Error -> {
                    loadState.error.localizedMessage?.toast(holder.mContext)
                    loading_view.visibility = View.GONE
                    error_text.visibility = View.VISIBLE
                    nomore_text.visibility = View.GONE
                    setOnClickListener {
                        retry?.onRetry()
                    }
                }
                else -> {
                    loading_view.visibility = View.GONE
                    error_text.visibility = View.GONE
                    setOnClickListener(null)
                    nomore_text.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): DataViewHolder {
        return DataViewHolder(R.layout.load_state, parent)
    }

    /**
     * 什么时候显示
     * loadState.endOfPaginationReached = 分页加载完毕
     */
    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        Log.i(Tag, "displayLoadStateAsItem: $loadState")
        return super.displayLoadStateAsItem(loadState) || loadState.endOfPaginationReached
    }

}