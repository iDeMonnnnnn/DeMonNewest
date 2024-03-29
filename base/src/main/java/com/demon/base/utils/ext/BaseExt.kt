package com.demon.base.utils.ext


/**
 * @author DeMon
 * Created on 2020/11/25.
 * E-mail idemon_liu@qq.com
 * Desc:
 */

/**
 * 获取当前类的TAG
 */
inline val <T : Any> T.Tag: String
    get() = this.javaClass.simpleName
