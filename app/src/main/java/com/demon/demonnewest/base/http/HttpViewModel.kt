package com.demon.demonnewest.base.http

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.demon.base.mvvm.BaseViewModel
import javax.inject.Inject

/**
 * @author DeMon
 * Created on 2020/7/21.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
open class HttpViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var repository: DataRepository

    protected fun <T : Any> toPage(block: suspend (Int) -> PageBean<T>) =
        Pager(PagingConfig(pageSize = 20)) {
            object : PagingSource<Int, T>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
                    return try {
                        val page: Int = params.key ?: 0 //当前页码，默认第一页为0
                        val bean = block.invoke(page)
                        val prevKey = if (page > 1) page - 1 else null
                        //bean.over==true，默认加载完成
                        val nextKey = if (!bean.over) page + 1 else null
                        LoadResult.Page(bean.datas, prevKey, nextKey)
                    } catch (e: Exception) {
                        LoadResult.Error(e)
                    }
                }

                override fun getRefreshKey(state: PagingState<Int, T>): Int? {
                    return state.anchorPosition?.let { anchorPosition ->
                        val anchorPage = state.closestPageToPosition(anchorPosition)
                        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                    }
                }
            }
        }.flow.cachedIn(viewModelScope)

}