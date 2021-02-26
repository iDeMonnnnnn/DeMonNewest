package com.demon.demonjetpack.module.dp.audio

/**
 * @author DeMon
 * Created on 2021/2/26.
 * E-mail 757454343@qq.com
 * Desc: 工厂管理类
 */
object AudioFactoryManager {
    fun getInstance(mode: Int): IAudio {
        return when (mode) {
            1 -> {
                MediaPlayerAudio()
            }
            else -> {
                MediaPlayerAudio()
            }
        }
    }
}