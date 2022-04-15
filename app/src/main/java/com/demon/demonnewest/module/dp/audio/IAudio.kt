package com.demon.demonnewest.module.dp.audio

import android.content.Context
import android.net.Uri

/**
 * @author DeMon
 * Created on 2021/2/26.
 * E-mail idemon_liu@qq.com
 * Desc: 音频公共接口
 */
interface IAudio {

    /**
     * 播放资源文件
     */
    fun load(context: Context, id: Int)

    /**
     * 播放
     */
    fun load(context: Context, uri: Uri)

    /**
     * 开始播放
     */
    fun start()

    /**
     * 暂停
     */
    fun pause()

    /**
     * 继续播放
     */
    fun keep()

    /**
     * 释放
     */
    fun release()

    /**
     * 跳到指定位置
     */
    fun seek(pos: Int)

    /**
     * 音频总时长
     */
    fun getDuration(): Int

    /**
     * 音频当前播放位置
     */
    fun getCurrentPosition(): Int

    /**
     * 设置统一监听
     */
    fun setIAudioListener(listener: IAudioListener?)
}

interface IAudioListener {
    fun playLoad(percent: Int) //加载进度

    fun playPrepared() //准备完成

    fun playCompletion() //播放完成

    fun playError(what: Int, extra: String) //播放异常
}