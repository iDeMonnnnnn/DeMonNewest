package com.demon.demonjetpack.module.dp.audio

import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.R
import com.demon.demonjetpack.databinding.ActivityAudioBinding
import java.util.*
import kotlin.concurrent.fixedRateTimer


class AudioActivity : MvvmActivity<ActivityAudioBinding, BaseViewModel>() {
    private var iIAudio: IAudio? = null
    private var timer: Timer? = null

    override fun init() {
        iIAudio = AudioFactoryManager.getInstance(0)
        iIAudio?.load(mContext, R.raw.huoche)
        iIAudio?.setIAudioListener(object : IAudioListener {
            override fun playLoad(percent: Int) {

            }

            override fun playPrepared() {
                binding.seekBar.max = iIAudio?.getDuration() ?: 0
                startTimer()
            }

            override fun playCompletion() {
                stopTimer()
            }

            override fun playError(what: Int, extra: Int) {
                stopTimer()
            }
        })
        binding.run {
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

    fun startTimer() {
        timer = fixedRateTimer(TAG, false, 0, 500) {
            binding.seekBar.progress = iIAudio?.getCurrentPosition() ?: 0
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