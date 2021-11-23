package com.demon.demonjetpack.module.img

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.demon.demonjetpack.R
import com.demon.demonjetpack.databinding.ListImgGlideBinding
import com.demon.demonjetpack.list.BaseAdapter
import com.demon.demonjetpack.list.DataViewHolder
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

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
            .dontAnimate()
            .transform(RoundedCornersTransformation(6, 0, RoundedCornersTransformation.CornerType.ALL))
        Glide.with(mContext)
            .asBitmap()
            .load(ImgLoadActivity.url1)
            .apply(options)
            .thumbnail(0.5f)
            .into(holder.binding.ivImage)
    }

}