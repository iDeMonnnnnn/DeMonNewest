package com.demon.demonnewest.module.paging

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.base.mvvm.MvvmActivity
import com.demon.demonnewest.base.data.RouterConst
import com.demon.demonnewest.base.list.DataViewHolder
import com.demon.demonnewest.bean.ArticleBean
import com.demon.demonnewest.databinding.ActivityPagingBinding
import com.demon.demonnewest.databinding.ListArticleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@Route(path = RouterConst.ACT_PAGING)
@AndroidEntryPoint
class PagingActivity : MvvmActivity<ActivityPagingBinding, PagingViewModel>() {


    private val pagingAdapter by lazy {
        object : PagingAdapter<ListArticleBinding, ArticleBean>(binding.swipe) {
            override fun onBind(holder: DataViewHolder<ListArticleBinding>, position: Int, data: ArticleBean) {
                holder.binding.run {
                    tvText.text = data.title
                }
            }
        }
    }

    override fun initData() {
        setToolbar("Paging3")
        binding.rv.adapter = pagingAdapter.withFooter()

        mViewModel.getAuthorList("鸿洋").observe(this@PagingActivity) {
            lifecycleScope.launch {
                pagingAdapter.submitData(it)
            }
        }


    }
}