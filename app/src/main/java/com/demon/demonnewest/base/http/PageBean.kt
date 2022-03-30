package com.demon.demonnewest.base.http
import com.google.gson.annotations.SerializedName


/**
 * @author DeMon
 * Created on 2020/9/29.
 * E-mail 757454343@qq.com
 * Desc:
 */
data class PageBean<T>(
    @SerializedName("curPage")
    val curPage: Int,
    @SerializedName("datas")
    val datas: MutableList<T>,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("over")
    val over: Boolean,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("total")
    val total: Int
)