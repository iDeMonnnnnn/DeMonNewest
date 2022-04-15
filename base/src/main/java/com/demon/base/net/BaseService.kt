package com.demon.base.net

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
interface BaseService {
    @GET
    operator fun get(@Url url: String): Call<String>

    @GET
    operator fun get(@Url url: String, @QueryMap maps: Map<String, Any>): Call<String>

    @FormUrlEncoded
    @GET
    fun getForm(@Url url: String, @FieldMap maps: Map<String, Any>): Call<String>

    @POST
    fun post(@Url url: String): Call<String>

    @FormUrlEncoded
    @POST
    fun postForm(@Url url: String, @FieldMap maps: Map<String, Any>): Call<String>

    @POST
    fun post(@Url url: String, @Body jsonObject: JSONObject): Call<String>

    @Multipart
    @POST
    fun uploadsFile(@Url url: String, @Part part: MultipartBody.Part): Call<String>

    @Multipart
    @POST
    fun uploadFiles(@Url url: String, @PartMap maps: Map<String, RequestBody>): Call<String>


}