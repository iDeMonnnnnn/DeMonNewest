package com.demon.easyjetpack.module.paging

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.demon.easyjetpack.base.http.HttpViewModel

/**
 * @author DeMon
 * Created on 2020/9/28.
 * E-mail 757454343@qq.com
 * Desc:
 */
class PagingViewModel @ViewModelInject constructor() : HttpViewModel() {

    val liveData = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 20)
    ) {
        PagingDataSource(repository)
    }.flow.cachedIn(viewModelScope).asLiveData()


}