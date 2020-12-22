package com.demon.demonjetpack.module.paging

import androidx.hilt.lifecycle.ViewModelInject
import com.demon.demonjetpack.base.ext.getDataOrThrow
import com.demon.demonjetpack.base.http.HttpViewModel

/**
 * @author DeMon
 * Created on 2020/9/28.
 * E-mail 757454343@qq.com
 * Desc:
 */
class PagingViewModel @ViewModelInject constructor() : HttpViewModel() {

    val liveData = toPage {
        repository.articleAuthorList(it).getDataOrThrow()
    }
}