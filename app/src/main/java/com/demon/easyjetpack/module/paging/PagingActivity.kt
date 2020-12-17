package com.demon.easyjetpack.module.paging

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.easyjetpack.R
import com.demon.easyjetpack.base.data.RouterConst
import com.demon.easyjetpack.bean.ArticleBean
import com.demon.easyjetpack.list.DataViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_paging.*
import kotlinx.android.synthetic.main.list_paging.view.*
import kotlinx.coroutines.launch

@Route(path = RouterConst.ACT_PAGING)
@AndroidEntryPoint
class PagingActivity : MvvmActivity<PagingViewModel>() {

    override fun setupLayoutId(): Int = R.layout.activity_paging

    override fun init() {
        val pagingAdapter = object : PagingAdapter<ArticleBean>(R.layout.list_paging, swipe) {
            override fun onBind(holder: DataViewHolder, position: Int, data: ArticleBean) {
                holder.itemView.tv_text.text = data.title
            }
        }
        rv.adapter = pagingAdapter.withFooter()

        mViewModel.liveData.observe(this@PagingActivity) {
            lifecycleScope.launch {
                pagingAdapter.submitData(it)
            }
        }


    }
}