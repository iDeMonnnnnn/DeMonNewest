package com.demon.demonjetpack.module.fragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.demon.demonjetpack.base.ext.getDataOrThrow
import com.demon.demonjetpack.base.http.HttpViewModel

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
class FragmentViewModel @ViewModelInject constructor() : HttpViewModel() {

    val authorData = MutableLiveData<Any>()

    fun articleList(author: String) {
        authorData.toFlowLoading {
            repository.articleList(author).getDataOrThrow()
        }
    }
}