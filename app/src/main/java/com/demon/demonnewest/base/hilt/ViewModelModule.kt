package com.demon.demonnewest.base.hilt

import android.content.Context
import androidx.room.Room
import com.demon.base.net.BaseApi
import com.demon.demonnewest.BuildConfig
import com.demon.demonnewest.base.db.AppDatabase
import com.demon.demonnewest.base.db.UserDao
import com.demon.demonnewest.base.http.ApiService
import com.demon.demonnewest.base.http.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail idemon_liu@qq.com
 * Desc:ViewModel注入
 */
@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideService(): ApiService = BaseApi().setLog(BuildConfig.DEBUG)
        //.setCache(true)
        .getRetrofit("https://www.wanandroid.com/")
        .create(ApiService::class.java)

    @Provides
    fun provideDataRepository(apiService: ApiService) = DataRepository(apiService)

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "AppRoom.db").build()

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()


}