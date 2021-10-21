package com.demon.demonjetpack.module.dp

import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.R
import com.demon.demonjetpack.databinding.ActivityAudioBinding
import com.demon.demonjetpack.module.dp.audio.AudioFactoryManager
import com.demon.demonjetpack.module.dp.audio.IAudio
import com.demon.demonjetpack.module.dp.audio.IAudioListener
import java.util.*
import kotlin.concurrent.fixedRateTimer

class AudioActivity : MvvmActivity<ActivityAudioBinding, BaseViewModel>() {
    private var iIAudio: IAudio? = null
    private var timer: Timer? = null
    private var player: Int = 1
    override fun init() {
        setToolbar("工厂模式")
        binding.run {
            btnPlayer.setOnClickListener {
                initPlayer()
            }
            btnStart.setOnClickListener {
                iIAudio?.start()
            }

            btnPause.setOnClickListener {
                iIAudio?.pause()
            }

            btnKeep.setOnClickListener {
                iIAudio?.keep()
            }
        }
    }

    fun changeButton(isEnabled: Boolean) {
        binding.run {
            btnStart.isEnabled = isEnabled
            btnPause.isEnabled = isEnabled
            btnKeep.isEnabled = isEnabled
        }
    }

    fun initPlayer() {
        iIAudio = if (player == 0) {
            player++
            binding.btnPlayer.text = "切换播放器（ExoPlayer）"
            AudioFactoryManager.getInstance(AudioFactoryManager.ExoPlayer)
        } else {
            player--
            binding.btnPlayer.text = "切换播放器（MediaPlayer）"
            AudioFactoryManager.getInstance(AudioFactoryManager.MediaPlayer)
        }
        iIAudio?.load(mContext, R.raw.huoche)
        iIAudio?.setIAudioListener(object : IAudioListener {
            override fun playLoad(percent: Int) {
                changeButton(false)
            }

            override fun playPrepared() {
                binding.seekBar.max = iIAudio?.getDuration() ?: 0
                binding.seekBar.progress = 0
                changeButton(true)
                startTimer()
            }

            override fun playCompletion() {
                stopTimer()
            }

            override fun playError(what: Int, extra: String) {
                stopTimer()
            }
        })
    }

    fun startTimer() {
        timer = fixedRateTimer(TAG, false, 0, 500) {
            runOnUiThread {
                binding.seekBar.progress = iIAudio?.getCurrentPosition() ?: 0
            }
        }
    }

    private fun stopTimer() {
        timer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        iIAudio?.release()
        timer?.cancel()
    }

}