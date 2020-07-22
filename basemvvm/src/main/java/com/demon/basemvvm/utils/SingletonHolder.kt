package com.demon.basemvvm.utils

/**
 * @author DeMon
 * Created on 2020/7/20.
 * E-mail 757454343@qq.com
 * Desc:
 */
open class SingletonHolder<out T, in A>(creator: (A) -> T) {

    private var creator: ((A) -> T) = creator

    @Volatile
    private var instance: T? = null

    fun getInstance(a: A): T = instance ?: synchronized(this) {
        instance ?: creator(a).apply {
            instance = this
        }
    }

}