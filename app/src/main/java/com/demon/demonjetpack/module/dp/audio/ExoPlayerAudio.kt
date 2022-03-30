package com.demon.demonjetpack.module.dp.audio

import android.content.Context
import android.net.Uri
import com.tencent.mars.xlog.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.upstream.RawResourceDataSource


/**
 * @author DeMon
 * Created on 2021/2/26.
 * E-mail 757454343@qq.com
 * Desc: 系统MediaPlayer实现
 */
class ExoPlayerAudio : IAudio {
    private val TAG = "ExoPlayerAudio"
    private var mPlayer: SimpleExoPlayer? = null
    private var playWhenReady: Boolean = false

    init {
        Log.i(TAG, "using: ExoPlayerAudio")
    }

    override fun load(context: Context, id: Int) {
        if (mPlayer == null) {
            mPlayer = SimpleExoPlayer.Builder(context).build()
        } else {
            mPlayer?.stop(true)
        }
        val mediaItem: MediaItem = MediaItem.fromUri(RawResourceDataSource.buildRawResourceUri(id))
        mPlayer?.setMediaItem(mediaItem)
        mPlayer?.prepare()
    }

    override fun load(context: Context, uri: Uri) {
        if (mPlayer == null) {
            mPlayer = SimpleExoPlayer.Builder(context).build()
        } else {
            mPlayer?.stop(true)
        }
        val mediaItem: MediaItem = MediaItem.fromUri(uri)
        mPlayer?.setMediaItem(mediaItem)
        mPlayer?.prepare()
    }

    override fun start() {
        mPlayer?.run {
            seek(0)
            play()
        }
    }

    override fun pause() {
        mPlayer?.run {
            if (isPlaying) pause()
        }
    }

    override fun keep() {
        mPlayer?.run {
            if (!isPlaying) play()
        }
    }

    override fun seek(pos: Int) {
        mPlayer?.run {
            if (pos in 0 until duration) seekTo(pos.toLong())
        }
    }


    override fun release() {
        mPlayer?.run {
            stop(true)
            release()
        }
    }

    override fun getDuration(): Int = (mPlayer?.duration ?: 0).toInt()

    override fun getCurrentPosition(): Int = (mPlayer?.currentPosition ?: 0).toInt()

    override fun setIAudioListener(listener: IAudioListener?) {
        mPlayer?.addListener(object : Player.EventListener {
            override fun onPlaybackStateChanged(state: Int) {
                super.onPlaybackStateChanged(state)
                when (state) {
                    Player.STATE_BUFFERING -> {
                        listener?.playLoad(0)
                    }
                    Player.STATE_IDLE -> {
                    }
                    Player.STATE_READY -> {
                        if (playWhenReady) listener?.playPrepared()
                    }
                    Player.STATE_ENDED -> {
                        listener?.playCompletion()
                    }
                }
            }

            override fun onPlayWhenReadyChanged(ready: Boolean, reason: Int) {
                super.onPlayWhenReadyChanged(playWhenReady, reason)
                playWhenReady = ready
            }

            override fun onPlayerError(error: ExoPlaybackException) {
                super.onPlayerError(error)
                listener?.playError(error.type, error.message ?: "")
            }

            override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                super.onTimelineChanged(timeline, reason)
            }
        })

    }
}