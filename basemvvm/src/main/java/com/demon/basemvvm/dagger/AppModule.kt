package com.demon.basemvvm.dagger

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import dagger.Binds
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
abstract class AppModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @Singleton
    abstract fun bindContext(application: Application): Context

}