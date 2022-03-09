package com.demon.demonjetpack.base.hilt

import com.demon.demonjetpack.module.hilt.ElectricEngine
import com.demon.demonjetpack.module.hilt.Engine
import com.demon.demonjetpack.module.hilt.GasEngine
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

/**
 * @author DeMon
 * Created on 2022/3/9.
 * E-mail idemon_liu@qq.com
 * Desc:
 */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HiltGasEngine

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HiltElectricEngine


@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {

    @HiltGasEngine
    @Binds
    abstract fun bindGasEngine(engine: GasEngine): Engine

    @HiltElectricEngine
    @Binds
    abstract fun bindElectricEngine(engine: ElectricEngine): Engine
}