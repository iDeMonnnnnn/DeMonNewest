package com.demon.easyjetpack.base.ext

/**
 * @author DeMon
 * Created on 2020/9/30.
 * E-mail 757454343@qq.com
 * Desc:
 */

/**
 * 获取当前类的TAG
 */
inline val <T : Any> T.TAG: String
    get() = this.javaClass.simpleName