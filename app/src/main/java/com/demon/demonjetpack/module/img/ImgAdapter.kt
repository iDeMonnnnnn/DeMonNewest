package com.demon.demonjetpack.module.img

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.demon.demonjetpack.R
import com.demon.demonjetpack.base.list.BaseAdapter
import com.demon.demonjetpack.base.list.DataViewHolder
import com.demon.demonjetpack.databinding.ListImgGlideBinding

/**
 * @author DeMon
 * Created on 2021/11/22.
 * E-mail 757454343@qq.com
 * Desc:
 */
class ImgAdapter : BaseAdapter<String, ListImgGlideBinding>() {

    override fun convert(holder: DataViewHolder<ListImgGlideBinding>, item: String) {
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