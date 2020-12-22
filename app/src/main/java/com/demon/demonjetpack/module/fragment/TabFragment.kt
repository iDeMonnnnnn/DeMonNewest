package com.demon.demonjetpack.module.fragment

import android.util.Log
import com.demon.basemvvm.mvvm.MvvmFragment
import com.demon.basemvvm.utils.Tag
import com.demon.demonjetpack.databinding.FragmentTabBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
@AndroidEntryPoint
class TabFragment constructor(var author: String) : MvvmFragment<FragmentTabBinding, FragmentViewModel>() {
    override fun init() {

    }

    override fun initViewModel() {
        mViewModel.run {
            articleList(author)
            authorData.observe(this@TabFragment) {
                Log.i(Tag, "initViewModel: $it")
                binding.text.text = it.toString()
            }
        }
    }

    override fun onResumeRefresh() {
        super.onResumeRefresh()
        Log.i(Tag, "onResumeRefresh $author")
    }


    override fun doOnErrLiveData(msg: String) {
        super.doOnErrLiveData(msg)
        Log.i(Tag, "doOnErrLiveData: $msg")
    }
}