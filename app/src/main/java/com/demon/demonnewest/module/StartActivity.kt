package com.demon.demonnewest.module

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import com.demon.base.mvvm.BaseVBActivity
import com.demon.base.utils.ext.toActivity
import com.demon.base.utils.ext.toast
import com.demon.demonnewest.databinding.ActivityStartBinding
import com.demon.demonnewest.module.home.HomeActivity
import com.tencent.mars.xlog.Log

/**
 * @author DeMonnnnnn
 * date 2022/7/29
 * email liu_demon@qq.com
 * desc
 */
class StartActivity : BaseVBActivity<ActivityStartBinding>() {

    override fun setupData() {/*        binding.motionLayout.setDebugMode(
                    if (BuildConfig.DEBUG) {
                        MotionLayout.DEBUG_SHOW_PATH
                    } else {
                        MotionLayout.DEBUG_SHOW_NONE
                    }
                )*/
        binding.motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                Log.i(TAG, "onTransitionStarted: ")
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                Log.i(TAG, "onTransitionCompleted: ")
                binding.shimmerLayout.startShimmer()
                binding.shimmerLayout.postDelayed({
                    toActivity<HomeActivity>()
                    finish()
                }, 1000)
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }
        })

        binding.root.post {
            binding.motionLayout.transitionToEnd()
        }

        intent.extras
        askNotificationPermission()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.shimmerLayout.stopShimmer()
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            "We need to notify you that permissions have brought you a better user experience ~".toast()
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                "We need to notify you that permissions have brought you a better user experience ~".toast()
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        val url = intent.extras?.getString("url")
        Log.i(TAG, "FCM click: $url")
        url?.run {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(this)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }


    }


}