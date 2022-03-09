package com.demon.demonjetpack.base.hilt

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc: 单例注入
 */
@Module
@InstallIn(SingletonComponent::class)
class SingleModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

}