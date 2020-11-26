package com.demon.easyjetpack.module.paging

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.basemvvm.utils.Tag
import com.demon.easyjetpack.R
import com.demon.easyjetpack.base.data.RouterConst
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_paging.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Route(path = RouterConst.ACT_PAGING)
@AndroidEntryPoint
class PagingActivity : MvvmActivity<PagingViewModel>() {

    override fun setupLayoutId(): Int = R.layout.activity_paging

    override fun init() {
        val pagingAdapter = PagingAdapter()
        rv.adapter = pagingAdapter.withLoadStateFooter(PagingLoadAdapter(pagingAdapter))
        pagingAdapter.addLoadStateListener {
            Log.i(Tag, "init: $it")
            swipe.isRefreshing = it.refresh is LoadState.Loading
            if (it.append.endOfPaginationReached) {//分页结束
            }
        }
        swipe.setOnRefreshListener {
            pagingAdapter.refresh()
        }

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.i(Tag, "onScrollStateChanged: $newState")
            }


            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.i(Tag, "onScrolled: ${recyclerView.scrollState}")
            }
        })

        lifecycleScope.launch {
            mViewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
            /*pagingAdapter.loadStateFlow.collectLatest {
                Log.i(Tag, "loadStateFlow: ")
                if (it.refresh !is LoadState.Loading) {
                    swipe.isRefreshing = false
                }
            }*/
        }
    }
}