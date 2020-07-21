package com.demon.easyjetpack.dagger

import android.content.Context
import androidx.room.Room
import com.demon.basemvvm.mvvm.BaseApi
import com.demon.easyjetpack.BuildConfig
import com.demon.easyjetpack.db.AppDatabase
import com.demon.easyjetpack.db.UserDao
import com.demon.easyjetpack.http.ApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
@Module
class SingleModule {

    @Provides
    @Singleton
    fun provideService(): ApiService = BaseApi().setLog(BuildConfig.DEBUG)
        .getRetrofit("https://www.wanandroid.com/")
        .create(ApiService::class.java)


    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()


    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "AppRoom.db").build()


    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()


}