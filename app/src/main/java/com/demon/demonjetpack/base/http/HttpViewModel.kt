package com.demon.demonjetpack.base.http

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.demonjetpack.App
import com.demon.demonjetpack.hilt.InfEntry
import dagger.hilt.EntryPoints
import javax.inject.Inject

/**
 * @author DeMon
 * Created on 2020/7/21.
 * E-mail 757454343@qq.com
 * Desc:
 */
open class HttpViewModel @Inject constructor() : BaseViewModel() {

    val repository: DataRepository by lazy {
        EntryPoints.get(App.appContext, InfEntry::class.java).provideDataRepository()
    }

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
            }
        }.flow.cachedIn(viewModelScope).asLiveData()

}