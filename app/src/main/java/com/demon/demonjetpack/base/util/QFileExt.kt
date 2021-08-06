package com.demon.demonjetpack.base.util

import android.content.Context
import android.graphics.Bitmap
import android.text.TextUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author DeMon
 * Created on 2021/8/5.
 * E-mail 757454343@qq.com
 * Desc:
 */
/**
 * getFilesDir和getCacheDir是在手机自带的一块存储区域(internal storage)，通常比较小，SD卡取出也不会影响到，App的sqlite数据库和SharedPreferences都存储在这里。所以这里应该存放特别私密重要的东西。
 *
 * getExternalFilesDir和getExternalCacheDir是在SD卡下(external storage)，在sdcard/Android/data/包名/files和sdcard/Android/data/包名/cache下，会跟随App卸载被删除。
 *
 * @param type The type of files directory to return. May be {@code null}
 *            for the root of the files directory or one of the following
 *            constants for a subdirectory
 * @see android.os.Environment.DIRECTORY_MUSIC,
 * @see android.os.Environment.DIRECTORY_PODCASTS,
 * @see android.os.Environment.DIRECTORY_RINGTONES,
 * @see android.os.Environment.DIRECTORY_ALARMS,
 * @see android.os.Environment.DIRECTORY_NOTIFICATIONS,
 * @see android.os.Environment.DIRECTORY_PICTURES,
 * @see android.os.Environment.DIRECTORY_MOVIES
 */
fun Context.getExternalOrFilesDir(type: String?): File {
    // 如果获取为空则改为getFilesDir
    val dir = getExternalFilesDir(type) ?: filesDir
    if (!dir.exists()) {
        dir.mkdirs()
    }
    return dir
}

/**
 * getExternalOrFilesDir().getAbsolutePath()
 * @see getExternalOrFilesDir
 */
fun Context.getExternalOrFilesDirPath(type: String?): String {
    return getExternalOrFilesDir(type).absolutePath
}

/**
 * getFilesDir和getCacheDir是在手机自带的一块存储区域(internal storage)，通常比较小，SD卡取出也不会影响到，App的sqlite数据库和SharedPreferences都存储在这里。所以这里应该存放特别私密重要的东西。
 *
 * getExternalFilesDir和getExternalCacheDir是在SD卡下(external storage)，在sdcard/Android/data/包名/files和sdcard/Android/data/包名/cache下，会跟随App卸载被删除。
 */
fun Context.getExternalOrCacheDir(): File {
    // 如果获取为空则改为getCacheDir
    val dir = externalCacheDir ?: cacheDir
    if (!dir.exists()) {
        dir.mkdirs()
    }
    return dir
}

fun Context.getExternalOrCacheDirPath(): String {
    return getExternalOrCacheDir().absolutePath
}

fun Context.getCacheChildDirPath(child: String? = "") = this.getCacheChildDir(child).absolutePath

/**
 * 在缓存目录下新键子目录
 */
fun Context.getCacheChildDir(child: String? = ""): File {
    val name = if (TextUtils.isEmpty(child)) {
        "app"
    } else {
        child
    }
    val file = File(getExternalOrCacheDir(), name)
    file.mkdirs()
    return file
}


fun Bitmap?.saveBitmapImg(context: Context): File? {
    this ?: return null
    //保存图片
    val file = File(context.getCacheChildDirPath(), "${System.currentTimeMillis()}.png")
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