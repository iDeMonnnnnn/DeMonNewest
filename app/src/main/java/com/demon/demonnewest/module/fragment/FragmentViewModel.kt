package com.demon.demonnewest.module.fragment

import androidx.lifecycle.MutableLiveData
import com.demon.base.utils.ext.toast
import com.demon.demonnewest.base.http.HttpViewModel
import com.demon.demonnewest.base.widget.RefreshLoadLayout
import com.demon.demonnewest.bean.ArticleBean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
@HiltViewModel
class FragmentViewModel @Inject constructor() : HttpViewModel() {

    val authorData = MutableLiveData<MutableList<ArticleBean>>()

    fun articleList(author: String, layout: RefreshLoadLayout) {
        onFlow(
            showLoading = false,
            request = {
                repository.articleAuthorList(author, layout.page)
            },
            each = {
                authorData.value = it.datas
                layout.finishRefreshLoad(!it.over)
            }, catch = {
                authorData.value = mutableListOf()
                it.message?.toast()
                layout.finishRefreshLoad(isLoadMore = true, isSucceed = false)
            }
        )
    }
}