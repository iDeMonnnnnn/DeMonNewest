package com.demon.basemvvm.mvvm

import android.app.Application
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
class BaseApi {

    //超时时长，单位：毫秒
    private var timeOut: Long = 10
    private var isCache = false
    private var isLog = true
    @Inject
    protected lateinit var application: Application

    fun setTimeOut(timeOut: Long): BaseApi {
        this.timeOut = timeOut
        return this
    }

    fun setCache(cache: Boolean): BaseApi {
        isCache = cache
        return this
    }

    fun setLog(log: Boolean): BaseApi {
        isLog = log
        return this
    }

    /**
     * 使用OkHttp配置了超时及缓存策略的Retrofit
     *
     * @param baseUrl
     * @param interceptors 自定义的拦截器
     * @return
     */
    fun getRetrofit(baseUrl: String, vararg interceptors: Interceptor): Retrofit {
        //创建一个OkHttpClient并设置超时时间
        val builder = OkHttpClient.Builder()
        builder.readTimeout(timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeOut, TimeUnit.SECONDS)
            .connectTimeout(timeOut, TimeUnit.SECONDS)
        if (isCache) {
            //缓存
            val cacheFile = File(application.cacheDir, "cache")
            val cache = Cache(cacheFile, 1024 * 1024 * 200) //200Mb
            builder.addInterceptor(CacheInterceptor())//缓存
                .addNetworkInterceptor(CacheInterceptor())//网络缓存
                .cache(cache)
        }
        if (isLog) {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logInterceptor)//日志
        }

        for (interceptor in interceptors) {
            builder.addInterceptor(interceptor)
        }

        val client = builder.build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())//请求结果转换为基本类型，一般为String
            .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
            .build()
    }

}