package com.demon.demonjetpack.base.widget

import android.content.Context
import android.util.AttributeSet
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @author DeMon
 * Created on 2021/11/24.
 * E-mail 757454343@qq.com
 * Desc: 封装RefreshLoadLayout，统一下拉刷新&加载更多的回调接口
 */
class RefreshLoadLayout constructor(context: Context, attrs: AttributeSet) : SmartRefreshLayout(context, attrs) {

    enum class ActionState {
        None, Refresh, LoadMore
    }


    var actionState: ActionState = ActionState.None

    var listener: RefreshLoadListener? = null

    var firstPage = 0 //首次加载页码

    var page = 0 //当前页码

    fun isRefreshAction() = page == firstPage

    init {
        setOnRefreshListener {
            page = firstPage
            actionState = ActionState.Refresh
            listener?.onRefreshLoad(this, true, page)
        }

        setOnLoadMoreListener {
            page++
            actionState = ActionState.LoadMore
            listener?.onRefreshLoad(this, false, page)
        }
    }

    fun autoOnlyRefresh() {
        page = firstPage
        actionState = ActionState.Refresh
        listener?.onRefreshLoad(this, true, page)
    }


    fun finishRefreshLoad(isLoadMore: Boolean, isSucceed: Boolean = true) {
        when (actionState) {
            ActionState.Refresh -> {
                finishRefresh(0, isSucceed, !isLoadMore)

            }
            ActionState.LoadMore -> {
                if (!isSucceed) page--
                finishLoadMore(0, isSucceed, !isLoadMore)
            }
        }
        actionState = ActionState.None
    }

}

interface RefreshLoadListener {
    fun onRefreshLoad(layout: RefreshLoadLayout, isRefresh: Boolean, page: Int)
}