package com.demon.demonnewest.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.ImageView
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import kotlin.math.roundToInt


/**
 * @author DeMonnnnnn
 * date 2022/10/26
 * email: liu_demon@qq.com
 * desc:
 */
object BitmapUtils {
    /**
     * 压缩质量：发送前要压缩的图片质量（0~100值）
     * 在魅族2上，微信先将图片缩小至75%后，再压缩质量（从400K左右到35K左右），经测试估计是质量75哦
     * 调整COMPRESS_QUALITY值可压缩图像大小，最大100. meizu2上100时400K左右，75时35K左右，再低则
     * 图像的大小变化不太明显了（20为13K左右）
     */
    const val COMPRESS_QUALITY = 75

    /**
     * 通过Uri获取Bitmap对象
     */
    @JvmStatic
    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = false
        //根据uri获取图片的流
        val inputStream = context.contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream, null, options)
    }

    /**
     * 质量压缩
     */
    @JvmStatic
    fun compressQuality(bitmap: Bitmap, quality: Int = COMPRESS_QUALITY): Bitmap? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos) //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        val isBm = ByteArrayInputStream(baos.toByteArray()) //把压缩后的数据baos存放到ByteArrayInputStream中
        val bm = BitmapFactory.decodeStream(isBm, null, null) //把ByteArrayInputStream数据生成图片

        baos.flush()
        baos.close()
        isBm.close()

        return bm
    }

    /**
     * 采样率压缩
     */
    @JvmStatic
    fun compressSampleSize(iv: ImageView, uri: Uri) = compressSampleSize(iv.context, uri, iv.width, iv.height)

    /**
     * 采样率压缩
     */
    @JvmStatic
    fun compressSampleSize(context: Context, uri: Uri, reqWidth: Int, reqHeight: Int): Bitmap? {
        var inputStream: InputStream? = null
        try {
            val options = BitmapFactory.Options()
            val angle = readPictureDegree(context, uri)
            Log.i("compressSampleSize", "angle=$angle")
            //禁止分配内存，只解析不加载内存中去
            options.inJustDecodeBounds = true
            //根据uri获取图片的流
            inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream, null, options)
            //算法计算采样率
            val inSampleSize = computeSampleSize(options, reqWidth, reqHeight)
            //设置采样率
            options.inSampleSize = inSampleSize
            //解析到内存中去
            options.inJustDecodeBounds = false
            // 根据uri重新获取流，inputstream在解析中发生改变了
            inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
            //大图角度偏移，旋转回来
            return if (bitmap != null) {
                val rotatedBitmap = rotateBitmap(bitmap, angle)
                if (rotatedBitmap != bitmap) {
                    //回收
                    bitmap.recycle()
                }
                rotatedBitmap
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }


    /**
     * 计算采样率
     */
    @JvmStatic
    fun computeSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // 计算原始图像的高度和宽度
        val height = options.outHeight
        val width = options.outWidth
        Log.d("computeSampleSize", "[原始options.outWidth=${options.outWidth},原始options.outHeight=${options.outHeight}]，\n[目标reqWidth=$reqWidth, 目标reqHeight=$reqHeight]")
        var inSampleSize = 1

        //判定，当原始图像的高和宽大于所需高度和宽度时
        if (height > reqHeight || width > reqWidth) {
            val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
            val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()

            //算出长宽比后去比例小的作为inSamplesize，保障最后imageview的dimension比request的大
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            //计算原始图片总像素
            val totalPixels = (width * height).toFloat()
            // Anything more than 2x the requested pixels we'll sample down further
            //所需总像素*2,长和宽的根号2倍
            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()

            //如果遇到很长，或者是很宽的图片时，这个算法比较有用
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }
        }
        Log.d("computeSampleSize", "计算后的inSampleSize=[$inSampleSize]")
        return inSampleSize
    }


    /**
     * 旋转照片
     */
    fun rotateBitmap(bitmap: Bitmap, angle: Int): Bitmap {
        if (angle == 0) {
            return bitmap
        }
        val m = Matrix()
        m.postRotate(angle.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)
    }

    /**
     * 获取照片角度
     * 大图压缩后，会出现旋转，需要获取角度矫正
     */
    fun readPictureDegree(context: Context, uri: Uri): Int {
        var degree = 0
        var inputStream: InputStream? = null
        try {
            inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.run {
                val exifInterface = ExifInterface(this)
                when (exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return degree
    }


    /**
     * 得到bitmap的大小
     */
    fun getBitmapSize(bitmap: Bitmap?): Int {
        bitmap ?: return 0
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            bitmap.allocationByteCount
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) { //API 12
            bitmap.byteCount
        } else {// 在低版本中用一行的字节x高度
            bitmap.rowBytes * bitmap.height
        }

    }
}