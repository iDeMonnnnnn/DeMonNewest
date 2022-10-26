package com.demon.demonnewest.module.list

import com.chad.library.adapter.base.BaseBinderAdapter
import com.demon.base.list.ListItemBinder
import com.demon.base.list.addListItemBinder
import com.demon.base.mvvm.BaseVBActivity
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.demonnewest.databinding.ActivityListBinding
import com.demon.demonnewest.module.list.adapter.OneItemBinder
import com.demon.demonnewest.module.list.adapter.TwoItemBinder
import com.demon.demonnewest.module.list.bean.OneBean
import com.demon.demonnewest.module.list.bean.TwoBean

/**
 * @author DeMon
 * Created on 2022/4/15.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class ListActivity : BaseVBActivity<ActivityListBinding>() {

    private val adapter by lazy {
        BaseBinderAdapter().apply {
            addListItemBinder<String, OneBean>(OneItemBinder())
            addListItemBinder<Int, TwoBean>(TwoItemBinder(), ListItemBinder.Grid, 4)
        }
    }

    override fun setupData() {
        setToolbar("多样式列表")
        adapter.addData(OneBean(mutableListOf("1", "2", "3", "4", "5")))
        adapter.addData(TwoBean(mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)))
        binding.rvData.adapter = adapter
    }
}