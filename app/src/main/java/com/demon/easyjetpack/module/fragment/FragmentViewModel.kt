package com.demon.easyjetpack.module.fragment

import androidx.lifecycle.MutableLiveData
import com.demon.easyjetpack.ext.getDataOrThrow
import com.demon.easyjetpack.http.HttpViewModel
import javax.inject.Inject

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
class FragmentViewModel @Inject constructor() : HttpViewModel() {

    val authorData = MutableLiveData<Any>()

    fun articleList(author: String) {
        authorData.toFlowLoading {
            repository.articleList(author).getDataOrThrow()
        }
    }
}