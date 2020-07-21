package com.demon.easyjetpack.http

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
class DataWrapper<T>(
    val errorMsg: String? = "",
    val errorCode: Int = 0,
    val data: T? = null
) {
    override fun toString(): String {
        return "MessageWrapper(errorMsg=$errorMsg, errorCode=$errorCode, data=$data)"
    }
}