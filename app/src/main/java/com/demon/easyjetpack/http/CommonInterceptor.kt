package com.demon.easyjetpack.http

import com.demon.easyjetpack.data.Constants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
class CommonInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()

        // 添加新的参数
        val authorizedUrlBuilder = oldRequest.url()
            .newBuilder()
            .scheme(oldRequest.url().scheme())
            .host(oldRequest.url().host())
            .addQueryParameter(Constants.KEY, Constants.KEY_VALUE)


        // 新的请求
        val newRequest = oldRequest.newBuilder()
            .method(oldRequest.method(), oldRequest.body())
            .url(authorizedUrlBuilder.build())
            .build()

        return chain.proceed(newRequest)

    }
}