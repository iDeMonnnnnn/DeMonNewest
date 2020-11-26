package com.demon.easyjetpack.base.http

import com.demon.basemvvm.mvvm.BaseViewModel
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
}