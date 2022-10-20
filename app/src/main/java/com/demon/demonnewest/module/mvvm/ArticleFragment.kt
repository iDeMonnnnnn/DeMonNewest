package com.demon.demonnewest.module.mvvm

import com.demon.base.list.BaseVbQAdapter
import com.tencent.mars.xlog.Log
import com.demon.base.mvvm.MvvmFragment
import com.demon.base.utils.ext.Tag
import com.demon.base.list.DataVbHolder
import com.demon.demonnewest.base.widget.RefreshLoadLayout
import com.demon.demonnewest.base.widget.RefreshLoadListener
import com.demon.demonnewest.bean.ArticleBean
import com.demon.demonnewest.databinding.FragmentArticleBinding
import com.demon.demonnewest.databinding.ListArticleBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
@AndroidEntryPoint
class ArticleFragment constructor(var author: String) : MvvmFragment<FragmentArticleBinding, ArticleViewModel>(), RefreshLoadListener {

    private val adapter by lazy {
        object : BaseVbQAdapter<ArticleBean, ListArticleBinding>() {

            override fun onBindItem(holder: DataVbHolder<ListArticleBinding>, binding: ListArticleBinding, data: ArticleBean, pos: Int) {
                binding.tvText.text = data.title
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: $author")
    }

    override fun initLazyData() {

        bindingRun {
            refreshLayout.listener = this@ArticleFragment
            refreshLayout.autoRefresh()

            rvData.adapter = adapter
        }

        vmRun {
            authorData.observe(this@ArticleFragment) {
                if (binding.refreshLayout.isRefreshAction()) {
                    adapter.setData(it)
                } else {
                    adapter.addData(it)
                }
            }
        }

    }

    override fun onReVisibleRefresh() {
        super.onReVisibleRefresh()
        Log.i(Tag, "onReVisibleRefresh $author")
        binding.refreshLayout.autoRefresh()
    }

    override fun onRefreshLoad(layout: RefreshLoadLayout, isRefresh: Boolean, page: Int) {
        vmRun {
            articleList(author, layout)
        }
    }


}