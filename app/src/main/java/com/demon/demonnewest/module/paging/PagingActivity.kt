package com.demon.demonnewest.module.paging

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.base.mvvm.MvvmActivity
import com.demon.demonnewest.base.data.RouterConst
import com.demon.base.list.DataVbHolder
import com.demon.base.mvvm.BaseVBActivity
import com.demon.demonnewest.bean.ArticleBean
import com.demon.demonnewest.databinding.ActivityPagingBinding
import com.demon.demonnewest.databinding.ListArticleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path = RouterConst.ACT_PAGING)
@AndroidEntryPoint
class PagingActivity : MvvmActivity<ActivityPagingBinding, PagingViewModel>() {


    private val pagingAdapter by lazy {
        object : PagingAdapter<ListArticleBinding, ArticleBean>(binding.swipe) {
            override fun onBind(holder: DataVbHolder<ListArticleBinding>, position: Int, data: ArticleBean) {
                holder.binding.run {
                    tvText.text = data.title
                }
            }
        }
    }

    override fun initData() {
        setToolbar("Paging3")
        binding.rv.adapter = pagingAdapter.withFooter()


        lifecycleScope.launch {
            mViewModel.getAuthorList("鸿洋").collect {
                pagingAdapter.submitData(it)
            }
        }
    }
}