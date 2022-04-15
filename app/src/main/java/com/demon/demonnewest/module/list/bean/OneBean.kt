package com.demon.demonnewest.module.list.bean

import com.demon.base.list.ListItem

/**
 * @author DeMon
 * Created on 2022/4/15.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class OneBean constructor(val data: MutableList<String> = mutableListOf()) : ListItem<String>(data) {
}


class TwoBean constructor(val data: MutableList<Int> = mutableListOf()) : ListItem<Int>(data) {
}