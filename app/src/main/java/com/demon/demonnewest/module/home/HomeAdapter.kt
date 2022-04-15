package com.demon.demonnewest.module.home

import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.demon.demonnewest.R
import com.demon.demonnewest.bean.HomeEntity

/**
 * @author DeMon
 * Created on 2021/10/21.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class HomeAdapter constructor(datas: MutableList<HomeEntity>) :
    BaseSectionQuickAdapter<HomeEntity, BaseViewHolder>(R.layout.list_home_head, R.layout.list_home_item, datas) {

    init {
        animationEnable = true
    }

    override fun convert(holder: BaseViewHolder, item: HomeEntity) {
        holder.setText(R.id.tvItem, item.name)
    }

    override fun convertHeader(helper: BaseViewHolder, item: HomeEntity) {
        helper.setText(R.id.tvHeader, item.title)
    }


}