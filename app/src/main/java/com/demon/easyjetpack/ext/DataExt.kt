package com.demon.easyjetpack.ext

import android.app.ProgressDialog
import android.content.Context
import com.demon.easyjetpack.data.Constants
import com.demon.easyjetpack.http.DataBean
import retrofit2.Call
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */


suspend fun <T : Any> Call<T>.getDataOrThrow(context: Context): T {
    val dialog = ProgressDialog(context)
    dialog.show()
    val result = awaitResult()
    dialog.dismiss()
    return when (result) {
        is Result.Ok -> {
            val data = result.value as DataBean
            when (data.getStatus()) {
                Constants.OK -> {
                    result.value
                }
                else -> {
                    throw Exception("获取数据失败！")
                }
            }
        }
        is Result.Error -> throw result.exception
        is Result.Exception -> throw result.exception
    }
}
