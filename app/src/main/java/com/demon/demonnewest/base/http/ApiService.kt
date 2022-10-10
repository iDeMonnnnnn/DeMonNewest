package com.demon.demonnewest.base.http


import com.demon.demonnewest.bean.ArticleBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
interface ApiService {

    @GET("article/list/{no}/json")
    fun articleList(@Path("no") no: Int, @Query("author") author: String): Call<DataWrapper<Any>>


    @GET("article/list/{no}/json")
    fun articleAuthorList(@Path("no") no: Int, @Query("author") author: String): Call<DataWrapper<PageBean<ArticleBean>>>

    @GET("article/list/{no}/json")
    fun articleListString(@Path("no") no: Int, @Query("author") author: String): Call<String>
}