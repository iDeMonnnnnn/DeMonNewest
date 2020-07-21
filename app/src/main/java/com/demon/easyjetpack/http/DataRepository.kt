package com.demon.easyjetpack.http

import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
@Singleton
class DataRepository @Inject constructor(private var apiService: ApiService) {

    fun articleList(author: String, no: Int = 0) = apiService.articleList(no, author)


    fun articleListString(author: String, no: Int = 0) = apiService.articleListString(no, author)
}