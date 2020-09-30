package com.demon.easyjetpack.module.paging

import androidx.paging.PagingSource
import com.demon.easyjetpack.base.ext.getDataOrThrow
import com.demon.easyjetpack.base.http.DataRepository
import com.demon.easyjetpack.bean.ArticleBean

/**
 * @author DeMon
 * Created on 2020/9/28.
 * E-mail 757454343@qq.com
 * Desc:
 */
class PagingDataSource constructor(private val repository: DataRepository) : PagingSource<Int, ArticleBean>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleBean> {
        return try {
            var page: Int?
            page = params.key ?: 0 //当前页码
            val bean = repository.articleAuthorList(page).getDataOrThrow()
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