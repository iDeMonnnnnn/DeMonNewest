package com.demon.demonnewest.module.camera

import android.Manifest
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import com.bumptech.glide.Glide
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.base.utils.setOnClickThrottleFirst
import com.demon.base.utils.ext.launchUI
import com.demon.base.utils.ext.toast
import com.demon.demonnewest.databinding.ActivityCameraxBinding
import com.demon.qfsolution.utils.getExternalOrFilesDirPath
import com.permissionx.guolindev.PermissionX
import java.io.File
import java.util.concurrent.Executors

/**
 * @author DeMonnnnnn
 * date 2022/6/30
 * email liu_demon@qq.com
 * desc
 */
class CameraXActivity : MvvmActivity<ActivityCameraxBinding, BaseViewModel>() {

    private var cameraController: LifecycleCameraController? = null
    private var isBack = true

    private lateinit var takePic: File

    override fun initData() {
        setToolbar("CameraX")

        PermissionX.init(this)
            .permissions(Manifest.permission.CAMERA)
            .request { allGranted, _, _ ->
                if (!allGranted) {
                    "没有相机权限无法使用拍照功能~".toast()
                    finish()
                } else {
                    bindPreview()
                }
            }

        binding.ivRotate.setOnClickThrottleFirst {
            isBack = !isBack
            switchCamera()
        }


        binding.ivTake.setOnClickThrottleFirst {

            takePic = File(getExternalOrFilesDirPath(Environment.DIRECTORY_PICTURES), "${System.currentTimeMillis()}.jpg")
            val metadata = ImageCapture.Metadata()
            //metadata.isReversedHorizontal = !isBack
            val outputFileOptions = ImageCapture.OutputFileOptions
                .Builder(takePic)
                .setMetadata(metadata)
                .build()
            cameraController?.takePicture(outputFileOptions, Executors.newCachedThreadPool(),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(error: ImageCaptureException) {
                        Log.e(TAG, "onError: ", error)
                    }

                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        outputFileResults.savedUri?.run {
                            takePicture(this)
                        }
                    }
                })
        }

        binding.ivBack.setOnClickThrottleFirst {
            takePic.delete()
            binding.groupTake.visibility = View.INVISIBLE
            binding.groupPreView.visibility = View.VISIBLE
        }

        binding.ivSure.setOnClickThrottleFirst {
            Log.i(TAG, "initData: ${takePic.absolutePath},exists=" + takePic.exists())
            finish()
        }
    }


    private fun takePicture(uri: Uri) {
        launchUI {
            binding.groupTake.visibility = View.VISIBLE
            binding.groupPreView.visibility = View.INVISIBLE
            Glide.with(this).load(uri).into(binding.preImage)
        }

    }


    private fun bindPreview() {

        /**
         * PERFORMANCE 是默认模式。PreviewView 会使用 SurfaceView 显示视频串流，但在某些情况下会回退为使用 TextureView。SurfaceView 具有专用的绘图界面，该对象更有可能通过内部硬件合成器实现硬件叠加层，尤
         * 其是当预览视频上面没有其他界面元素（如按钮）时。通过使用硬件叠加层进行渲染，视频帧会避开 GPU 路径，从而能降低平台功耗并缩短延迟时间。
         * COMPATIBLE 模式。在此模式下，PreviewView 会使用 TextureView；不同于 SurfaceView，该对象没有专用的绘图表面。因此，视频要通过混合渲染，才能显示。在这个额外的步骤中，应用可以执行额外的处理工作，例         * 如不受限制地缩放和旋转视频。
         */
        //binding.previewView.implementationMode = PreviewView.ImplementationMode.COMPATIBLE

        switchCamera()
        cameraController = LifecycleCameraController(this)
        /**
         * 如需缩短照片拍摄的延迟时间，请将 ImageCapture.CaptureMode 设置为 CAPTURE_MODE_MINIMIZE_LATENCY。
         * 如需优化照片质量，请将其设置为 CAPTURE_MODE_MAXIMIZE_QUALITY。
         */
        //cameraController?.imageCaptureMode = CAPTURE_MODE_MINIMIZE_LATENCY
        binding.previewView.controller = cameraController
        cameraController?.bindToLifecycle(this)
    }


    private fun switchCamera() {
        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(
                if (isBack) {
                    CameraSelector.LENS_FACING_BACK
                } else {
                    CameraSelector.LENS_FACING_FRONT
                }
            )
            .build()
        cameraController?.cameraSelector = cameraSelector
    }


}