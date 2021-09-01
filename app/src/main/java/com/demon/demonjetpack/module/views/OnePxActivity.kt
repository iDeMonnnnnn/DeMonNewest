package com.demon.demonjetpack.module.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.demon.demonjetpack.R


/**
 * @author DeMon
 * Created on 2021/8/31.
 * E-mail 757454343@qq.com
 * Desc:
 */
class OnePxActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actiivty_one_px)
    }

    private var fIntent: Intent? = null
    override fun onResume() {
        super.onResume()
        fIntent = Intent(this, FloatingService::class.java)
        startService(fIntent)
    }

    override fun onPause() {
        super.onPause()
        stopService(fIntent)
    }
}