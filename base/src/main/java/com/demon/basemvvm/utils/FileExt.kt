package com.demon.basemvvm.utils

import android.content.Context
import android.graphics.Bitmap
import com.demon.qfsolution.utils.getCacheChildDir
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author DeMon
 * Created on 2022/3/7.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
fun Bitmap?.saveBitmapImg(context: Context): File? {
    this ?: return null
    //保存图片
    val file = File(context.getCacheChildDir(null), "${System.currentTimeMillis()}.png")
    if (file.exists()) {
        file.delete()
    }
    var fileOutputStream: FileOutputStream? = null
    try {
        fileOutputStream = FileOutputStream(file)
        if (this.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
            fileOutputStream.flush()
            fileOutputStream.close()
            return file
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}