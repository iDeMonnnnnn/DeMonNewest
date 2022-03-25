package com.demon.demonjetpack.base.http

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.demon.base.mvvm.BaseViewModel
import javax.inject.Inject

/**
 * @author DeMon
 * Created on 2020/7/21.
 * E-mail 757454343@qq.com
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
                        var page: Int?
                        page = params.key ?: 0 //当前页码
                        val bean = block.invoke(page)
                        page = if (bean.over) {
                            null
                        } else {
                            page + 1
                        }
                        LoadResult.Page(bean.datas, null, page)
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
        }.flow.cachedIn(viewModelScope).asLiveData()

}