package com.demon.easyjetpack.dagger

import android.app.Application
import com.demon.basemvvm.dagger.AppModule
import com.demon.easyjetpack.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        ActivityBindModule::class,
        SingleModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}