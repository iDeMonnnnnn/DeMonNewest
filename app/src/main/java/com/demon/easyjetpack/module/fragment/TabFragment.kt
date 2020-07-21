package com.demon.easyjetpack.module.fragment

import android.util.Log
import androidx.lifecycle.observe
import com.demon.basemvvm.mvvm.MvvmFragment
import com.demon.easyjetpack.R
import kotlinx.android.synthetic.main.fragment_tab.*

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
class TabFragment constructor(var author: String) : MvvmFragment<FragmentViewModel>() {

    override fun setupLayoutId(): Int = R.layout.fragment_tab

    override fun init() {
        tvCity.text = author
    }

    override fun initViewModel() {
        mViewModel.run {
            articleList(author)
            authorData.observe(this@TabFragment) {
                Log.i(TAG, "initViewModel: $it")
                text.text = it.toString()
            }
        }
    }

    override fun onResumeRefresh() {
        super.onResumeRefresh()
        Log.i(TAG, "onResumeRefresh $author")
    }


    override fun doOnErrLiveData(msg: String) {
        super.doOnErrLiveData(msg)
        Log.i(TAG, "doOnErrLiveData: $msg")
    }
}