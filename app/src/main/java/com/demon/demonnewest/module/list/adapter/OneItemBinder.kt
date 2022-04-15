package com.demon.demonnewest.module.list.adapter

import com.demon.base.list.BaseVbItemBinder
import com.demon.base.list.DataVbHolder
import com.demon.demonnewest.databinding.ItemOneBinding

/**
 * @author DeMon
 * Created on 2022/4/15.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class OneItemBinder : BaseVbItemBinder<String, ItemOneBinding>() {

    override fun convertItem(holder: DataVbHolder<ItemOneBinding>, binding: ItemOneBinding, data: String, pos: Int) {
        binding.text.text = data
    }
}