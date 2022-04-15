package com.demon.demonnewest.module.paging

import com.demon.demonnewest.base.http.HttpViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author DeMon
 * Created on 2020/9/28.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
@HiltViewModel
class PagingViewModel @Inject constructor() : HttpViewModel() {

    fun getAuthorList(author: String) =
        toPage {
            repository.articleAuthorList(author, it)
        }

}