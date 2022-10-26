package com.demon.demonnewest.module.views

import android.view.View
import androidx.fragment.app.Fragment
import com.demon.base.mvvm.BaseVBActivity
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.base.utils.ext.showFragment
import com.demon.demonnewest.R
import com.demon.demonnewest.databinding.ActivityMotionBinding
import com.demon.demonnewest.module.views.motion.*

class MotionActivity : BaseVBActivity<ActivityMotionBinding>(), View.OnClickListener {


    override fun setupData() {
        setToolbar("MotionLayout")
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        addShow(v?.id ?: 0)
    }

    private var simpleFragment: SimpleFragment? = null

    private var parallaxFragment: ParallaxFragment? = null

    private var imageFilterFragment: ImageFilterFragment? = null

    private var fulCompactFragment: FulCompactFragment? = null

    private var textScatteredFragment: TextScatteredFragment? = null

    private var redPackageFragment: RedPackageFragment? = null

    private fun addShow(id: Int) {
        when (id) {
            R.id.btn1 -> {
                if (simpleFragment == null) {
                    simpleFragment = SimpleFragment()
                }
                showFragment(R.id.frameLayout, simpleFragment)
            }
            R.id.btn2 -> {
                if (parallaxFragment == null) {
                    parallaxFragment = ParallaxFragment()
                }
                showFragment(R.id.frameLayout, parallaxFragment)
            }
            R.id.btn3 -> {
                if (imageFilterFragment == null) {
                    imageFilterFragment = ImageFilterFragment()
                }
                showFragment(R.id.frameLayout, imageFilterFragment)
            }
            R.id.btn4 -> {
                if (fulCompactFragment == null) {
                    fulCompactFragment = FulCompactFragment()
                }
                showFragment(R.id.frameLayout, fulCompactFragment)
            }
            R.id.btn5 -> {
                if (textScatteredFragment == null) {
                    textScatteredFragment = TextScatteredFragment()
                }
                showFragment(R.id.frameLayout, fulCompactFragment)
            }
            R.id.btn6 -> {
                if (redPackageFragment == null) {
                    redPackageFragment = RedPackageFragment()
                }
                showFragment(R.id.frameLayout, fulCompactFragment)
            }
        }

    }

    private var fragment: Fragment? = null

    private fun replace(id: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (id) {
            R.id.btn1 -> fragment = SimpleFragment()
            R.id.btn2 -> fragment = ParallaxFragment()
            R.id.btn3 -> fragment = ImageFilterFragment()
            R.id.btn4 -> fragment = FulCompactFragment()
            R.id.btn5 -> fragment = TextScatteredFragment()
            R.id.btn6 -> fragment = RedPackageFragment()
        }
        fragment?.run {
            transaction.replace(R.id.frameLayout, this).commitAllowingStateLoss()
        }
    }
}