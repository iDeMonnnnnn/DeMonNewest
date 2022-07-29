package com.demon.demonnewest.module.img

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.demon.base.list.BaseVbAdapter
import com.demon.demonnewest.R
import com.demon.base.list.DataVbHolder
import com.demon.demonnewest.databinding.ListImgGlideBinding

/**
 * @author DeMon
 * Created on 2021/11/22.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class ImgAdapter : BaseVbAdapter<String, ListImgGlideBinding>() {


    override fun convertItem(holder: DataVbHolder<ListImgGlideBinding>, binding: ListImgGlideBinding, data: String, pos: Int) {
        val options = RequestOptions()
            .fitCenter()
            .error(R.drawable.default_image)
            .placeholder(R.drawable.default_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(mContext)
            .load(data)
            .apply(options)
            .into(holder.binding.ivImage)
    }

}