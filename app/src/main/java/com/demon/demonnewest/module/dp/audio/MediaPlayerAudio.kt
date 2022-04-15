package com.demon.demonnewest.module.dp.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.tencent.mars.xlog.Log

/**
 * @author DeMon
 * Created on 2021/2/26.
 * E-mail idemon_liu@qq.com
 * Desc: 系统MediaPlayer实现
 */
class MediaPlayerAudio : IAudio {
    private val TAG = "MediaPlayerAudio"
    private var mMediaPlayer: MediaPlayer? = null

    init {
        Log.i(TAG, "using: MediaPlayer")
        mMediaPlayer = MediaPlayer()
    }

    override fun load(context: Context, id: Int) {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer()
        } else {
            mMediaPlayer?.reset()
        }
        val afd = context.resources.openRawResourceFd(id)
        mMediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        mMediaPlayer?.prepareAsync()
    }

    override fun load(context: Context, uri: Uri) {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer()
        } else {
            mMediaPlayer?.reset()
        }
        mMediaPlayer?.setDataSource(context, uri)
        mMediaPlayer?.prepareAsync()
    }

    override fun start() {
        mMediaPlayer?.run {
            seek(0)
            start()
        }
    }

    override fun pause() {
        mMediaPlayer?.run {
            if (isPlaying) pause()
        }
    }

    override fun keep() {
        mMediaPlayer?.run {
            if (!isPlaying) start()
        }
    }

    override fun seek(pos: Int) {
        mMediaPlayer?.run {
            if (pos in 0 until duration) seekTo(pos)
        }
    }


    override fun release() {
        mMediaPlayer?.run {
            stop()
            reset()
            release()
        }
    }

    override fun getDuration(): Int = mMediaPlayer?.duration ?: 0

    override fun getCurrentPosition(): Int = mMediaPlayer?.currentPosition ?: 0

    override fun setIAudioListener(listener: IAudioListener?) {
        mMediaPlayer?.setOnBufferingUpdateListener { _, percent ->
            listener?.playLoad(percent)
        }
        mMediaPlayer?.setOnErrorListener { _, what, extra ->
            listener?.playError(what, "$extra")
            false
        }
        mMediaPlayer?.setOnCompletionListener {
            listener?.playCompletion()
        }
        mMediaPlayer?.setOnPreparedListener {
            listener?.playPrepared()
        }
    }
}