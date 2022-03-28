package com.demon.base.utils


/**
 * @author DeMon
 * Created on 2020/11/25.
 * E-mail 757454343@qq.com
 * Desc:
 */

/**
 * 获取当前类的TAG
 */
inline val <T : Any> T.Tag: String
    get() = this.javaClass.simpleName

fun <T> Boolean.whatIf(ifTrue: T, other: T): T {
    return if (this) {
        ifTrue
    } else {
        other
    }
}

fun <T> Boolean.whatIf(ifBlock: () -> T, elseBlock: () -> T): T {
    return if (this) {
        ifBlock()
    } else {
        elseBlock()
    }
}