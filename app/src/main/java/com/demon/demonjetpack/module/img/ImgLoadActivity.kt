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
        const val url1 = "https://a1-vip6.easemob.com/utfqvfhpyygy/yhgw/chatfiles/0f87be10-485f-11ec-a5a1-5dba1a443b69"
        const val url2 = "https://a1-vip6.easemob.com/utfqvfhpyygy/yhgw/chatfiles/17981010-485e-11ec-95c3-6d895c003136"
    }

    override fun init() {

        val datas = mutableListOf(url1, url2)
        adapter.datas = datas
        binding.rv.adapter = adapter
    }
}