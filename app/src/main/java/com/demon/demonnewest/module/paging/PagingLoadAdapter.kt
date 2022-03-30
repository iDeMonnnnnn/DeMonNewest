package com.demon.demonnewest.module.paging

import com.tencent.mars.xlog.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.demon.base.utils.ext.Tag
import com.demon.base.utils.ext.toast
import com.demon.demonnewest.base.list.DataViewHolder
import com.demon.demonnewest.databinding.LoadStateBinding

/**
 * @author DeMon
 * Created on 2020/9/29.
 * E-mail 757454343@qq.com
 * Desc:
 */

interface RetryListener {
    fun onRetry()
}

class PagingLoadAdapter constructor(private val retry: RetryListener? = null) : LoadStateAdapter<DataViewHolder<LoadStateBinding>>() {

    override fun onBindViewHolder(holder: DataViewHolder<LoadStateBinding>, loadState: LoadState) {
        holder.binding.run {
            when (loadState) {
                is LoadState.Loading -> {
                    loadingView.visibility = View.VISIBLE
                    errorText.visibility = View.GONE
                    nomoreText.visibility = View.GONE
                    root.setOnClickListener(null)
                }
                is LoadState.Error -> {
                    loadState.error.localizedMessage?.toast()
                    loadingView.visibility = View.GONE
                    errorText.visibility = View.VISIBLE
                    nomoreText.visibility = View.GONE
                    root.setOnClickListener {
                        retry?.onRetry()
                    }
                }
                else -> {
                    loadingView.visibility = View.GONE
                    errorText.visibility = View.GONE
                    nomoreText.visibility = View.VISIBLE
                    root.setOnClickListener(null)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): DataViewHolder<LoadStateBinding> {
        return DataViewHolder(LoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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