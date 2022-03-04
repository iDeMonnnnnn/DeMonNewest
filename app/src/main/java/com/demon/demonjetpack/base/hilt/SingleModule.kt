package com.demon.demonjetpack.base.hilt

import android.content.Context
import androidx.room.Room
import com.demon.basemvvm.net.BaseApi
import com.demon.demonjetpack.BuildConfig
import com.demon.demonjetpack.base.db.AppDatabase
import com.demon.demonjetpack.base.db.UserDao
import com.demon.demonjetpack.base.http.ApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideService(): ApiService = BaseApi().setLog(BuildConfig.DEBUG)
        //.setCache(true)
        .getRetrofit("https://www.wanandroid.com/")
        .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "AppRoom.db").build()


    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()


}