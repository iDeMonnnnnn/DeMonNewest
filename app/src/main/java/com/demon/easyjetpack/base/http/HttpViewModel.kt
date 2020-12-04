package com.demon.easyjetpack.base.http

import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.easyjetpack.App
import com.demon.easyjetpack.hilt.InfEntry
import dagger.hilt.EntryPoints
import javax.inject.Inject

/**
 * @author DeMon
 * Created on 2020/7/21.
 * E-mail 757454343@qq.com
 * Desc:
 */
open class HttpViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var repository: DataRepository


    init {
        repository = EntryPoints.get(App.appContext, InfEntry::class.java).provideDataRepository()
    }
}