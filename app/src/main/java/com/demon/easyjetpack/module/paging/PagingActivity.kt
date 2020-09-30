package com.demon.easyjetpack.module.paging

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.easyjetpack.R
import com.demon.easyjetpack.base.data.RouterConst
import kotlinx.android.synthetic.main.activity_paging.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Route(path = RouterConst.ACT_PAGING)
class PagingActivity : MvvmActivity<PagingViewModel>() {

    override fun setupLayoutId(): Int = R.layout.activity_paging

    override fun init() {
        val pagingAdapter = PagingAdapter()
        rv.adapter = pagingAdapter.withLoadStateFooter(PagingLoadAdapter(pagingAdapter))
        pagingAdapter.addLoadStateListener {
            Log.i(TAG, "init: $it")
            swipe.isRefreshing = it.refresh is LoadState.Loading
            if (it.append.endOfPaginationReached) {//分页结束
            }
        }
        swipe.setOnRefreshListener {
            pagingAdapter.refresh()
        }

        lifecycleScope.launch {
            mViewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
            /*pagingAdapter.loadStateFlow.collectLatest {
                Log.i(TAG, "loadStateFlow: ")
                if (it.refresh !is LoadState.Loading) {
                    swipe.isRefreshing = false
                }
            }*/
        }
    }
}