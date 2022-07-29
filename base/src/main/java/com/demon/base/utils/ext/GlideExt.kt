package com.demon.base.utils.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.demon.base.R

/**
 * @author DeMonnnnnn
 * date 2022/7/29
 * email liu_demon@qq.com
 * desc
 */


fun ImageView?.loadImg(
    url: String?,
    @DrawableRes place: Int = R.drawable.pic_default,
    @DrawableRes error: Int = R.drawable.pic_default
) {
    this ?: return
    url ?: return

    val option = RequestOptions().centerCrop().placeholder(place).error(error)
    Glide.with(context).load(url).apply(option).into(this)
}


fun ImageView?.loadCircleImg(
    url: String?,
    @DrawableRes place: Int = R.drawable.pic_default,
    @DrawableRes error: Int = R.drawable.pic_default
) {
    this ?: return
    url ?: return
    val option = RequestOptions().centerCrop().circleCrop().placeholder(place).error(error)
    Glide.with(context).load(url).apply(option).into(this)
}


fun ImageView?.loadCircleImg(@DrawableRes res: Int = R.drawable.pic_default) {
    this ?: return
    val option = RequestOptions().centerCrop().circleCrop()
    Glide.with(context).load(res).apply(option).into(this)
}