package com.demon.demonnewest.base.hilt

import com.google.gson.Gson
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author DeMon
 * Created on 2020/11/26.
 * E-mail 757454343@qq.com
 * Desc:非作用域的类注入（类似Dagger的inject）
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface UnscopedEntryPoint {

    fun provideGson(): Gson

}