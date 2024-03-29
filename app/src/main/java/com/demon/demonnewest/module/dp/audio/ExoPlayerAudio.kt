package com.demon.demonnewest.module.dp.audio

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.tencent.mars.xlog.Log


/**
 * @author DeMon
 * Created on 2021/2/26.
 * E-mail idemon_liu@qq.com
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
            mPlayer?.stop()
        }
        val mediaItem: MediaItem = MediaItem.fromUri(RawResourceDataSource.buildRawResourceUri(id))
        mPlayer?.setMediaItem(mediaItem)
        mPlayer?.prepare()
    }

    override fun load(context: Context, uri: Uri) {
        if (mPlayer == null) {
            mPlayer = SimpleExoPlayer.Builder(context).build()
        } else {
            mPlayer?.stop()
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
            stop()
            release()
        }
    }

    override fun getDuration(): Int = (mPlayer?.duration ?: 0).toInt()

    override fun getCurrentPosition(): Int = (mPlayer?.currentPosition ?: 0).toInt()

    override fun setIAudioListener(listener: IAudioListener?) {
        mPlayer?.addListener(object : Player.Listener {
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

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                listener?.playError(error.errorCode, error.message ?: "")
            }

            override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                super.onTimelineChanged(timeline, reason)
            }
        })

    }
}