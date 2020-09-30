package com.demon.easyjetpack.base.http


import com.demon.easyjetpack.bean.ArticleBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
interface ApiService {

    @GET("article/list/{no}/json")
    fun articleList(@Path("no") no: Int, @Query("author") author: String): Call<DataWrapper<Any>>


    @GET("article/list/{no}/json")
    fun articleAuthorList(@Path("no") no: Int,@Query("author") author: String): Call<DataWrapper<PageBean<ArticleBean>>>

    @GET("article/list/{no}/json")
    fun articleListString(@Path("no") no: Int, @Query("author") author: String): Call<String>
}