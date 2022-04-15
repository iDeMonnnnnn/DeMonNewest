package com.demon.demonnewest.module.dp.audio

/**
 * @author DeMon
 * Created on 2021/2/26.
 * E-mail idemon_liu@qq.com
 * Desc: 工厂管理类
 */
object AudioFactoryManager {
    const val MediaPlayer = 0
    const val ExoPlayer = 1

    fun getInstance(mode: Int): IAudio {
        return when (mode) {
            1 -> {
                ExoPlayerAudio()
            }
            else -> {
                MediaPlayerAudio()
            }
        }
    }
}