package com.demon.demonjetpack.module.fragment

import com.tencent.mars.xlog.Log
import com.demon.base.mvvm.MvvmFragment
import com.demon.base.utils.ext.Tag
import com.demon.demonjetpack.base.list.BaseAdapter
import com.demon.demonjetpack.base.list.DataViewHolder
import com.demon.demonjetpack.base.widget.RefreshLoadLayout
import com.demon.demonjetpack.base.widget.RefreshLoadListener
import com.demon.demonjetpack.bean.ArticleBean
import com.demon.demonjetpack.databinding.FragmentTabBinding
import com.demon.demonjetpack.databinding.ListArticleBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
@AndroidEntryPoint
class TabFragment constructor(var author: String) : MvvmFragment<FragmentTabBinding, FragmentViewModel>(), RefreshLoadListener {

    private val adapter by lazy {
        object : BaseAdapter<ArticleBean, ListArticleBinding>() {
            override fun convert(holder: DataViewHolder<ListArticleBinding>, item: ArticleBean) {
                holder.binding.tvText.text = item.title
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: $author")
    }

    override fun initData() {

        bindingRun {
            refreshLayout.listener = this@TabFragment
            refreshLayout.autoRefresh()

            rvData.adapter = adapter
        }

        vmRun {
            authorData.observe(this@TabFragment) {
                if (binding.refreshLayout.isRefreshAction()) {
                    adapter.setData(it)
                } else {
                    adapter.addData(it)
                }
            }
        }

    }

    override fun onResumeRefresh() {
        super.onResumeRefresh()
        Log.i(Tag, "onResumeRefresh $author")
        binding.refreshLayout.autoRefresh()
    }

    override fun onRefreshLoad(layout: RefreshLoadLayout, isRefresh: Boolean, page: Int) {
        vmRun {
            articleList(author, layout)
        }
    }


}