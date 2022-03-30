package com.demon.demonnewest.module.img

import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.demonnewest.databinding.ActivityImgLoadBinding

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