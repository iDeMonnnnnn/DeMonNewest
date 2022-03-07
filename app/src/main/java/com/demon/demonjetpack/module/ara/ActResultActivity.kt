package com.demon.demonjetpack.module.ara

import android.Manifest
import android.os.Environment
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.demon.basemvvm.intent.DeMonActivityResult
import com.demon.basemvvm.intent.forActivityResult
import com.demon.basemvvm.intent.pairIntent
import com.demon.basemvvm.intent.toActivity
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.basemvvm.utils.setOnClickThrottleFirst
import com.demon.demonjetpack.databinding.ActivityActResultBinding
import com.demon.qfsolution.utils.MimeType
import com.demon.qfsolution.utils.getExternalOrFilesDirPath
import com.demon.qfsolution.utils.getFileUri
import java.io.File

class ActResultActivity : MvvmActivity<ActivityActResultBinding, BaseViewModel>() {

    private val requestPermission = DeMonActivityResult(this, ActivityResultContracts.RequestPermission())
    private val requestMultiplePermissions = DeMonActivityResult(this, ActivityResultContracts.RequestMultiplePermissions())
    private val takePicture = DeMonActivityResult(this, ActivityResultContracts.TakePicture())
    private val captureVideo = DeMonActivityResult(this, ActivityResultContracts.CaptureVideo())
    private val pickContact = DeMonActivityResult(this, ActivityResultContracts.PickContact())
    private val getContent = DeMonActivityResult(this, ActivityResultContracts.GetContent())
    private val openDocument = DeMonActivityResult(this, ActivityResultContracts.OpenDocument())
    private val openMultipleDocuments = DeMonActivityResult(this, ActivityResultContracts.OpenMultipleDocuments())
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
                }
            }

            btn2.setOnClickThrottleFirst {
                requestMultiplePermissions.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Log.i(TAG, "requestMultiplePermissions: $it")
                }
            }

            btn3.setOnClickThrottleFirst {
                val savePath = "${getExternalOrFilesDirPath(Environment.DIRECTORY_PICTURES)}/${System.currentTimeMillis()}.jpg"
                val uri = File(savePath).getFileUri()
                takePicture.launch(uri) {
                    Log.i(TAG, "takePicture: $it")
                }
            }

            btn4.setOnClickThrottleFirst {
                val savePath = "${getExternalOrFilesDirPath(Environment.DIRECTORY_DCIM)}/${System.currentTimeMillis()}.mp4"
                val uri = File(savePath).getFileUri()
                captureVideo.launch(uri) {
                    Log.i(TAG, "captureVideo: $it")
                }
            }

            btn5.setOnClickThrottleFirst {
                pickContact.launch(null) {
                    Log.i(TAG, "pickContact: $it")
                }
            }

            btn6.setOnClickThrottleFirst {
                getContent.launch(MimeType.img) {
                    Log.i(TAG, "getContent: $it")
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
                val uri = File(getExternalOrFilesDirPath(null)).getFileUri()
                openDocumentTree.launch(uri){
                    Log.i(TAG, "openDocumentTree: $it")
                }
            }

        }

    }
}