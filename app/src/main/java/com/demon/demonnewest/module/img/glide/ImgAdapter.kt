package com.demon.demonnewest.module.img.glide

import com.demon.base.list.BaseVbAdapter
import com.demon.base.list.DataVbHolder
import com.demon.base.utils.ext.loadImg
import com.demon.demonnewest.databinding.ListImgGlideBinding

/**
 * @author DeMon
 * Created on 2021/11/22.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class ImgAdapter : BaseVbAdapter<String, ListImgGlideBinding>() {


    override fun onBindItem(holder: DataVbHolder<ListImgGlideBinding>, binding: ListImgGlideBinding, data: String, pos: Int) {
        binding.ivImage.loadImg(data)
    }

}