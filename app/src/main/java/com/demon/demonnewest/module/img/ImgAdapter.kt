package com.demon.demonnewest.module.img

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.demon.base.list.BaseAdapter
import com.demon.demonnewest.R
import com.demon.base.list.DataVbHolder
import com.demon.demonnewest.databinding.ListImgGlideBinding

/**
 * @author DeMon
 * Created on 2021/11/22.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class ImgAdapter : BaseAdapter<String, ListImgGlideBinding>() {

    override fun convert(holder: DataVbHolder<ListImgGlideBinding>, item: String) {
        val options = RequestOptions()
            .fitCenter()
            .error(R.drawable.default_image)
            .placeholder(R.drawable.default_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(mContext)
            .load(item)
            .apply(options)
            .into(holder.binding.ivImage)
    }

}