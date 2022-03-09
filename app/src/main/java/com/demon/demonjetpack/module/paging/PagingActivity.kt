package com.demon.demonjetpack.module.paging

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.base.data.RouterConst
import com.demon.demonjetpack.base.list.DataViewHolder
import com.demon.demonjetpack.bean.ArticleBean
import com.demon.demonjetpack.databinding.ActivityPagingBinding
import com.demon.demonjetpack.databinding.ListArticleBinding
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