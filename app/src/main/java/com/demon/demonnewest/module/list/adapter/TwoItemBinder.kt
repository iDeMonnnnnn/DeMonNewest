package com.demon.demonnewest.module.list.adapter

import com.demon.base.list.BaseVbItemBinder
import com.demon.base.list.DataVbHolder
import com.demon.demonnewest.databinding.ItemOneBinding
import com.demon.demonnewest.databinding.ItemTwoBinding

/**
 * @author DeMon
 * Created on 2022/4/15.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class TwoItemBinder : BaseVbItemBinder<Int, ItemTwoBinding>() {

    override fun convertItem(holder: DataVbHolder<ItemTwoBinding>, binding: ItemTwoBinding, data: Int, pos: Int) {
        binding.text.text = "$data"
    }
}