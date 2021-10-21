package com.demon.demonjetpack.bean

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * @author DeMon
 * Created on 2021/10/21.
 * E-mail 757454343@qq.com
 * Desc:
 */
class HomeEntity : SectionEntity {

    var title: String = ""
    var name: String = ""
    var activity: Class<*>? = null
    var router: String = ""

    constructor(title: String) {
        this.title = title
    }

    constructor(name: String, activity: Class<*>?) {
        this.name = name
        this.activity = activity
    }

    constructor(name: String, router: String) {
        this.name = name
        this.router = router
    }

    override val isHeader: Boolean
        get() = title.isNotBlank()
}