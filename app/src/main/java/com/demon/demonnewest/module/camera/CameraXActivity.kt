package com.demon.demonnewest.module.camera

import android.Manifest
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.base.utils.click.setOnClickThrottleFirst
import com.demon.demonnewest.databinding.ActivityCameraxBinding
import com.demon.qfsolution.utils.getExternalOrCacheDirPath
import com.demon.qfsolution.utils.getExternalOrFilesDir
import com.demon.qfsolution.utils.getExternalOrFilesDirPath
import com.google.common.util.concurrent.ListenableFuture
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

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private var cameraProvider: ProcessCameraProvider? = null
    private var imageCapture: ImageCapture? = null
    private var isBack = true

    private lateinit var takePic: File

    override fun initData() {
        setToolbar("CameraX")

        PermissionX.init(this)
            .permissions(Manifest.permission.CAMERA)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {

                } else {
                    Toast.makeText(this, "没有相机权限无法使用拍照功能~", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindPreview()
        }, ContextCompat.getMainExecutor(this))


        binding.ivRotate.setOnClickThrottleFirst {
            isBack = !isBack
        }


        binding.ivTake.setOnClickThrottleFirst {

            takePic = File(getExternalOrFilesDirPath(Environment.DIRECTORY_PICTURES), "${System.currentTimeMillis()}.jpg")
            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(takePic).build()
            imageCapture?.takePicture(outputFileOptions, Executors.newCachedThreadPool(),
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
            binding.groupTake.visibility = View.INVISIBLE
            binding.groupPreView.visibility = View.VISIBLE
        }

        binding.ivSure.setOnClickThrottleFirst {
            Log.i(TAG, "initData: ${takePic.absolutePath},exists=" + takePic.exists())
            finish()
        }
    }


    private fun takePicture(uri: Uri) {
        runOnUiThread {
            binding.groupTake.visibility = View.VISIBLE
            binding.groupPreView.visibility = View.INVISIBLE
            Glide.with(this).load(uri).into(binding.preImage)
        }

    }


    private fun bindPreview() {
        val preview: Preview = Preview.Builder().build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(
                if (isBack) {
                    CameraSelector.LENS_FACING_BACK
                } else {
                    CameraSelector.LENS_FACING_FRONT
                }
            )
            .build()

        preview.setSurfaceProvider(binding.previewView.surfaceProvider)

        imageCapture = ImageCapture.Builder().build()

        var camera = cameraProvider?.bindToLifecycle(this, cameraSelector, imageCapture, preview)
    }


}