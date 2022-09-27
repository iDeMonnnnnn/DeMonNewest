package com.demon.base.list

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseBinderAdapter
import com.demon.base.databinding.ItemListBinding
import com.demon.base.utils.ext.getTClassIndex
import com.demon.base.utils.ext.whatIf
import com.google.android.flexbox.FlexboxLayoutManager

/**
 * @author DeMon
 * Created on 2022/4/14.
 * E-mail idemon_liu@qq.com
 * Desc: 通用列表 ItemBinder，每个Item都是一个列表
 */
inline fun <reified T : Any, reified Y : ListItem<T>> BaseBinderAdapter.addListItemBinder(
        binder: BaseVbItemBinder<T, *>,
        type: Int = ListItemBinder.Linear,
        spanCount: Int = -1
) {
    addItemBinder(ListItemBinder<T, Y>(binder, type, spanCount))
}

class ListItemBinder<T : Any, Y : ListItem<T>> : BaseVbItemBinder<Y, ItemListBinding> {

    companion object {
        const val Linear = 0
        const val Grid = 1
        const val StaggeredGrid = 2
        const val Flexbox = 3
    }

    lateinit var binder: BaseVbItemBinder<T, *>

    /**
     * 布局方向，线性布局默认是水平方向，其他布局默认是垂直
     */
    var orientation: Int = RecyclerView.VERTICAL

    var type: Int = Linear

    var spanCount: Int = -1

    var itemDecoration: RecyclerView.ItemDecoration? = null

    constructor(binder: BaseVbItemBinder<T, *>) {
        this.binder = binder
        this.type = Linear
        this.orientation = (type == Linear).whatIf(RecyclerView.HORIZONTAL, RecyclerView.VERTICAL)
    }

    constructor(binder: BaseVbItemBinder<T, *>, type: Int = Linear) {
        this.binder = binder
        this.type = type
        this.orientation = (type == Linear).whatIf(RecyclerView.HORIZONTAL, RecyclerView.VERTICAL)
    }

    constructor(binder: BaseVbItemBinder<T, *>, type: Int = Linear, spanCount: Int = -1) {
        this.binder = binder
        this.type = type
        this.spanCount = spanCount
        this.orientation = (type == Linear).whatIf(RecyclerView.HORIZONTAL, RecyclerView.VERTICAL)
    }


    private val ad by lazy {
        BaseBinderAdapter().apply {
            addItemBinder(binder.getTClassIndex(), binder)
        }
    }


    override fun convertItem(holder: DataVbHolder<ItemListBinding>, binding: ItemListBinding, data: Y, pos: Int) {
        ad.setList(data.list)
        itemDecoration?.run {
            binding.rvData.addItemDecoration(this)
        }
        binding.rvData.layoutManager = when (type) {
            Grid -> {
                GridLayoutManager(mContext, (spanCount == -1).whatIf(data.list.size, spanCount), orientation, false)
            }
            StaggeredGrid -> {
                StaggeredGridLayoutManager((spanCount == -1).whatIf(data.list.size, spanCount), orientation)
            }
            Flexbox -> {
                FlexboxLayoutManager(mContext)
            }
            else -> {
                LinearLayoutManager(mContext, orientation, false)
            }
        }
        binding.rvData.adapter = ad
    }
}