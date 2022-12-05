package com.demon.demonnewest.module.img.bitmap

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.demon.base.mvvm.BaseVBActivity
import com.demon.base.utils.ext.launchIO
import com.demon.base.utils.ext.launchUI
import com.demon.base.utils.ext.setOnClickThrottleFirst
import com.demon.base.utils.ext.toast
import com.demon.demonnewest.databinding.ActivityBitmapBinding
import com.demon.demonnewest.utils.BitmapUtils
import com.demon.qfsolution.utils.openPhotoAlbum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author DeMonnnnnn
 * date 2022/10/26
 * email: liu_demon@qq.com
 * desc:
 */
class BitmapActivity : BaseVBActivity<ActivityBitmapBinding>() {


    private var uri: Uri? = null
    override fun setupData() {
        setToolbar("Bitmap")

        bindingRun {
            btn1.setOnClickThrottleFirst {
                launchUI {
                    uri = openPhotoAlbum<Uri>()
                    iv1.setImageURI(uri)
                }
            }

            btn2.setOnClickThrottleFirst {
                uri?.run {
                    launchIO {
                        val bm = BitmapUtils.compressSampleSize(iv2, this)
                        withContext(Dispatchers.Main) {
                            iv2.setImageBitmap(bm)
                        }
                    }
                } ?: run {
                    "先选择图片~".toast()
                }
            }

            btn3.setOnClickThrottleFirst {

            }
        }
    }


}