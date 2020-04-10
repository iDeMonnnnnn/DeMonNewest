package com.demon.easyjetpack.http


import com.demon.easyjetpack.data.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
interface ApiService {

    @GET("now")
    fun getNowWeather(@Query(Constants.LOCATION) location: String): Call<DataBean>

}