package com.demon.basemvvm.mvvm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
class CacheInterceptor constructor(val context: Context): Interceptor {
    /**
     * 设缓存有效期为7天
     */
    private val CACHE_STALE_SEC = (60 * 60 * 24 * 7).toLong()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!isNetConnected(context)) {
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }
        val originalResponse = chain.proceed(request)
        return if (isNetConnected(context)) {
            originalResponse.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public,max-age" + 0)
                .build()
        } else {
            originalResponse.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached, max-stale=$CACHE_STALE_SEC")
                .build()
        }
    }

    /**
     * 判断网络状态
     */
    private fun isNetConnected(context: Context): Boolean {
        //获取系统的连接服务
        val connectivity = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        @SuppressLint("MissingPermission") val info = connectivity.activeNetworkInfo
        if (info != null && info.isConnected) {
            // 当前网络是连接的
            if (info.state == NetworkInfo.State.CONNECTED) {
                // 当前所连接的网络可用
                return true
            }
        }
        return false
    }
}