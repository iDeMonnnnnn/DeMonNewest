package com.demon.easyjetpack.module.fragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.easyjetpack.base.ext.getDataOrThrow
import com.demon.easyjetpack.base.http.DataRepository

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
class FragmentViewModel @ViewModelInject constructor(private val repository: DataRepository) : BaseViewModel() {

    val authorData = MutableLiveData<Any>()

    fun articleList(author: String) {
        authorData.toFlowLoading {
            repository.articleList(author).getDataOrThrow()
        }
    }
}