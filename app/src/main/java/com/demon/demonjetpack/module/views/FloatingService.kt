package com.demon.demonjetpack.module.views

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import android.view.View.OnTouchListener
import com.demon.basemvvm.helper.ActivityHelper
import com.demon.demonjetpack.databinding.FloatViewBinding

/**
 * @author DeMon
 * Created on 2021/8/31.
 * E-mail 757454343@qq.com
 * Desc:
 */
class FloatingService : Service() {
    private var windowManager: WindowManager? = null
    private var layoutParams: WindowManager.LayoutParams? = null
    private var binding: FloatViewBinding? = null

    private var isGlobal = false

    override fun onCreate() {
        super.onCreate()
        val activity = ActivityHelper.getTopActivity()
        binding = FloatViewBinding.inflate(LayoutInflater.from(applicationContext))
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        layoutParams = WindowManager.LayoutParams()
        layoutParams?.run {
            if (isGlobal) {
                // 系统全局窗口，可覆盖在任何应用之上，以及单独显示在桌面上
                // 安卓6.0 以后，全局的Window类别，必须使用TYPE_APPLICATION_OVERLAY
                type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                else WindowManager.LayoutParams.TYPE_PHONE
            } else {
                // 设置窗口类型为应用子窗口，和PopupWindow同类型
                type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
                // 必须指定token
                token = activity?.window?.decorView?.windowToken
            }

            format = PixelFormat.RGBA_8888
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            gravity = Gravity.RIGHT //悬浮框在布局的位置
            width = WindowManager.LayoutParams.WRAP_CONTENT //悬浮窗的宽，不指定则无法滑动
            height = WindowManager.LayoutParams.WRAP_CONTENT //悬浮窗的高，不指定则无法滑动
            //layoutParams.x = 0; //初始位置的x坐标
            //layoutParams.y = 0; //初始位置的y坐标
        }
        binding?.close?.setOnClickListener {
            stopSelf() //关闭当前服务
        }
        binding?.root?.setOnClickListener(View.OnClickListener {
            if (binding?.close?.visibility == View.GONE) {
                binding?.close?.visibility = View.VISIBLE
            } else {
                binding?.close?.visibility = View.GONE
            }
        })
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        showFloatingWindow()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun showFloatingWindow() {
        windowManager?.addView(binding?.root, layoutParams)
        binding?.root?.setOnTouchListener(FloatingOnTouchListener())
    }

    private inner class FloatingOnTouchListener : OnTouchListener {
        private var x = 0
        private var y = 0
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX.toInt()
                    y = event.rawY.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    val nowX = event.rawX.toInt()
                    val nowY = event.rawY.toInt()
                    val movedX = nowX - x
                    val movedY = nowY - y
                    x = nowX
                    y = nowY
                    layoutParams?.run {
                        x += movedX
                        y += movedY
                    }
                    //Log.i(TAG, "onTouch: " + layoutParams.x + " " + layoutParams.y);
                    windowManager!!.updateViewLayout(view, layoutParams)
                }
                else -> {
                }
            }
            return false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager?.removeViewImmediate(binding?.root)
    }

    companion object {
        private const val TAG = "FloatingService"
    }
}