package com.demon.demonjetpack.module.motion

import android.view.View
import androidx.fragment.app.Fragment
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.R
import com.demon.demonjetpack.databinding.ActivityMotionBinding

class MotionActivity : MvvmActivity<ActivityMotionBinding, BaseViewModel>(), View.OnClickListener {


    override fun init() {
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
    }

    lateinit var fragment: Fragment

    override fun onClick(v: View?) {
        val transaction = supportFragmentManager.beginTransaction()
        when (v?.id) {
            R.id.btn1 -> fragment = SimpleFragment()
            R.id.btn2 -> fragment = ParallaxFragment()
            R.id.btn3 -> fragment = ImageFilterFragment()
            R.id.btn4 -> fragment = FulCompactFragment()
        }
        transaction.replace(R.id.frameLayout, fragment).commitAllowingStateLoss()
    }
}