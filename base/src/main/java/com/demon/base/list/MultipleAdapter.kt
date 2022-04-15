package com.demon.base.list

import com.chad.library.adapter.base.BaseBinderAdapter

/**
 * @author DeMon
 * Created on 2022/4/15.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class MultipleAdapter constructor(data: MutableList<Any> = mutableListOf()) : BaseBinderAdapter(data) {

    @JvmOverloads
  inline  fun <reified T : Any,reified Y : ListItem<T>> addListItemBinder(
        clazz: Class<out T>, clazzy: Class<out Y>,
        binder: BaseVbItemBinder<T, *>,
        type: Int = ListItemBinder.Horizontal,
        spanCount: Int = -1
    ) {
        addItemBinder(ListItemBinder<T,Y>(binder, type, spanCount))
    }

    /*inline fun <reified T : Any, reified Y : ListItem<T>> addListItemBinder(
        binder: BaseVbItemBinder<T, *>,
        type: Int = ListItemBinder.Horizontal,
        spanCount: Int = -1
    ) {
        addItemBinder(ListItemBinder<T, Y>(binder, type, spanCount))
    }*/

}