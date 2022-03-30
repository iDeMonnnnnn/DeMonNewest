package com.demon.base.utils.ext

import android.content.Context
import android.graphics.Bitmap
import com.demon.qfsolution.utils.getCacheChildDir
import com.demon.qfsolution.utils.saveToAlbum
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author DeMon
 * Created on 2022/3/7.
 * E-mail idemon_liu@qq.com
 * Desc: 文件相关拓展
 */
/**
 * 将Bitmap保存为文件，
 *
 * @param saveToAlbum 是否保存到相册
 */
fun Bitmap?.saveBitmapImg(context: Context, saveToAlbum: Boolean = false): File? {
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
            if (saveToAlbum) {
                file.saveToAlbum()
            }
            return file
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}