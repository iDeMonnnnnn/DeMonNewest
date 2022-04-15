package com.demon.demonnewest.base.http

import com.demon.demonnewest.base.ext.getDataOrThrow
import javax.inject.Inject

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class DataRepository @Inject constructor(private val apiService: ApiService) {

    fun articleList(author: String, no: Int = 0) = apiService.articleList(no, author)

    suspend fun articleAuthorList(author: String, no: Int = 0) = apiService.articleAuthorList(no, author).getDataOrThrow()

    fun articleListString(author: String, no: Int = 0) = apiService.articleListString(no, author)
}