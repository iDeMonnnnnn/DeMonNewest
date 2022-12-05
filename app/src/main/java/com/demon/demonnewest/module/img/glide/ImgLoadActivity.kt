package com.demon.demonnewest.module.img.glide

import com.demon.base.mvvm.BaseVBActivity
import com.demon.demonnewest.databinding.ActivityImgLoadBinding

/**
 * @author DeMon
 * Created on 2021/11/20.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class ImgLoadActivity : BaseVBActivity<ActivityImgLoadBinding>() {

    private val adapter by lazy { ImgAdapter() }

    companion object {
        const val url1 = "https://idemon.oss-cn-guangzhou.aliyuncs.com/D.png"
        const val url2 = "https://idemon.oss-cn-guangzhou.aliyuncs.com/timg.gif"
    }

    override fun setupData() {
        setToolbar("Glide")
        val datas = mutableListOf(url1, url2)
        adapter.datas = datas
        binding.rv.adapter = adapter
    }
}