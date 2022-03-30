package com.demon.demonnewest.base.ext

import com.demon.demonnewest.base.http.DataWrapper
import retrofit2.Call
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
suspend fun <T : Any> Call<DataWrapper<T>>.getDataOrThrow(): T {
    return when (val result = awaitResult()) {
        is Result.Ok -> {
            val data = result.value.data
            //Log.i("DataWrapper", "getDataOrThrow: $data")
            when (result.value.errorCode) {
                0 -> {
                    if (data == null) {
                        throw Exception("获取数据为空！")
                    }
                    data
                }
                else -> {
                    throw Exception(result.value.errorMsg)
                }
            }
        }
        is Result.Error -> throw result.exception
        is Result.Exception -> throw result.exception
    }
}

suspend fun <T : Any> Call<T>.getData(): T {
    return when (val result = awaitResult()) {
        is Result.Ok -> result.value
        is Result.Error -> throw result.exception
        is Result.Exception -> throw result.exception
    }
}
