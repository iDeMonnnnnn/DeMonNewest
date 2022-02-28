package com.demon.demonjetpack.module.img

import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.databinding.ActivityImgLoadBinding

/**
 * @author DeMon
 * Created on 2021/11/20.
 * E-mail 757454343@qq.com
 * Desc:
 */
class ImgLoadActivity : MvvmActivity<ActivityImgLoadBinding, BaseViewModel>() {

    private val adapter by lazy { ImgAdapter() }

    companion object {
        const val url1 = "https://idemon.oss-cn-guangzhou.aliyuncs.com/D.png"
        const val url2 = "https://idemon.oss-cn-guangzhou.aliyuncs.com/timg.gif"
    }

    override fun initData() {

        val datas = mutableListOf(url1, url2)
        adapter.datas = datas
        binding.rv.adapter = adapter
    }
}