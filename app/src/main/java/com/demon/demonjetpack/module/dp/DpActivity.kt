package com.demon.demonjetpack.module.dp

import com.demon.basemvvm.intent.toActivity
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.databinding.ActivityDpBinding
import com.demon.demonjetpack.module.dp.audio.AudioActivity

class DpActivity : MvvmActivity<ActivityDpBinding, BaseViewModel>() {

    override fun init() {
        binding.btnFactory.setOnClickListener {
            toActivity(AudioActivity::class.java)
        }
    }
}