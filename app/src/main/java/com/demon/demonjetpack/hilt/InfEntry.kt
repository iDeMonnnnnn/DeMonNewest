package com.demon.demonjetpack.hilt

import com.demon.demonjetpack.base.http.DataRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * @author DeMon
 * Created on 2020/11/26.
 * E-mail 757454343@qq.com
 * Desc:非作用域的类注入（类似Dagger的inject）
 */
@EntryPoint
@InstallIn(ApplicationComponent::class)
interface InfEntry {

    fun provideDataRepository(): DataRepository

}