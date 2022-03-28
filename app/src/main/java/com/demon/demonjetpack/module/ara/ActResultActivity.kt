package com.demon.demonjetpack.module.ara

import android.Manifest
import android.os.Environment
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.demon.base.ara.DeMonActivityResult
import com.demon.base.ara.forActivityResult
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.base.utils.*
import com.demon.demonjetpack.databinding.ActivityActResultBinding
import com.demon.qfsolution.utils.MimeType
import com.demon.qfsolution.utils.getExternalOrFilesDirPath
import com.demon.qfsolution.utils.getFileUri
import com.demon.qfsolution.utils.isFileExists
import java.io.File

class ActResultActivity : MvvmActivity<ActivityActResultBinding, BaseViewModel>() {

    private val requestPermission = DeMonActivityResult(this, ActivityResultContracts.RequestPermission())
    private val requestMultiplePermissions = DeMonActivityResult(this, ActivityResultContracts.RequestMultiplePermissions())
    private val takePicture = DeMonActivityResult(this, ActivityResultContracts.TakePicture())
    private val takePicturePreview = DeMonActivityResult(this, ActivityResultContracts.TakePicturePreview())
    private val captureVideo = DeMonActivityResult(this, ActivityResultContracts.CaptureVideo())
    private val pickContact = DeMonActivityResult(this, ActivityResultContracts.PickContact())
    private val getContent = DeMonActivityResult(this, ActivityResultContracts.GetContent())
    private val getMultipleContents = DeMonActivityResult(this, ActivityResultContracts.GetMultipleContents())
    private val openDocument = DeMonActivityResult(this, ActivityResultContracts.OpenDocument())
    private val openMultipleDocuments = DeMonActivityResult(this, ActivityResultContracts.OpenMultipleDocuments())
    private val createDocument = DeMonActivityResult(this, ActivityResultContracts.CreateDocument())
    private val openDocumentTree = DeMonActivityResult(this, ActivityResultContracts.OpenDocumentTree())

    override fun initData() {
        setToolbar("Activity Result API")

        bindingRun {
            btn.setOnClickThrottleFirst {
                toActivity<ActResultTwoActivity>("string" to TAG)
            }

            btn0.setOnClickThrottleFirst {
                forActivityResult(
                    pairIntent<ActResultTwoActivity>(
                        "string" to TAG,
                        "timestamp" to System.currentTimeMillis()
                    ),
                    isCanBack = false
                ) {
                    val str = it?.getStringExtra("string") ?: ""
                    btn0.text = str
                }
            }

            btn1.setOnClickThrottleFirst {
                requestPermission.launch(Manifest.permission.CAMERA) {
                    Log.i(TAG, "requestPermission: $it")
                    if (it) {
                        "获取相机权限成功".toast()
                    }
                }
            }

            btn2.setOnClickThrottleFirst {
                requestMultiplePermissions.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Log.i(TAG, "requestMultiplePermissions: $it")
                    it.toString().toast()
                }
            }

            btn3.setOnClickThrottleFirst {
                if (!checkPermission(Manifest.permission.CAMERA)) {
                    "请先获取相机权限~".toast()
                    return@setOnClickThrottleFirst
                }
                val savePath = "${getExternalOrFilesDirPath(Environment.DIRECTORY_PICTURES)}/${System.currentTimeMillis()}.jpg"
                val uri = File(savePath).getFileUri()
                takePicture.launch(uri) {
                    Log.i(TAG, "takePicture: $it")
                }
            }

            btn4.setOnClickThrottleFirst {
                if (!checkPermission(Manifest.permission.CAMERA)) {
                    "请先获取相机权限~".toast()
                    return@setOnClickThrottleFirst
                }
                takePicturePreview.launch(null) {
                    Log.i(TAG, "takePicturePreview: $it")
                }
            }

            btn5.setOnClickThrottleFirst {
                getContent.launch(MimeType.all) {
                    Log.i(TAG, "getContent: $it  ${it.isFileExists()}")
                }
            }

            btn6.setOnClickThrottleFirst {
                getMultipleContents.launch(MimeType.all) {
                    Log.i(TAG, "getMultipleContents: $it")
                }
            }

            btn7.setOnClickThrottleFirst {
                openDocument.launch(arrayOf(MimeType.all)) {
                    Log.i(TAG, "openDocument: $it")
                }
            }

            btn8.setOnClickThrottleFirst {
                openMultipleDocuments.launch(arrayOf(MimeType.all)) {
                    Log.i(TAG, "openMultipleDocuments: $it")
                }
            }

            btn9.setOnClickThrottleFirst {
                val uri = getExternalOrFilesDirPath(null).toUri()
                openDocumentTree.launch(uri) {
                    Log.i(TAG, "openDocumentTree: $it")
                }
            }

            btn10.setOnClickThrottleFirst {
                createDocument.launch("${System.currentTimeMillis()}.txt") {
                    Log.i(TAG, "createDocument: $it")
                }
            }

            btn11.setOnClickThrottleFirst {
                if (!checkPermission(Manifest.permission.CAMERA)) {
                    "请先获取相机权限~".toast()
                    return@setOnClickThrottleFirst
                }
                val savePath = "${getExternalOrFilesDirPath(Environment.DIRECTORY_DCIM)}/${System.currentTimeMillis()}.mp4"
                val uri = File(savePath).getFileUri()
                captureVideo.launch(uri) {
                    Log.i(TAG, "captureVideo: $it")
                }
            }

            btn12.setOnClickThrottleFirst {
                pickContact.launch(null) {
                    Log.i(TAG, "pickContact: $it")
                }
            }

        }

    }
}