package com.demon.demonjetpack.base.util

import android.util.DisplayMetrics
import com.demon.demonjetpack.App
import kotlin.math.cos
import kotlin.math.sin


/**
 * @author DeMon
 * Created on 2021/8/4.
 * E-mail 757454343@qq.com
 * Desc:
 */
object UiUtils {

    @JvmStatic
    fun dp2px(dp: Float): Int {
        val metrics: DisplayMetrics = App.appContext.resources.displayMetrics
        return (metrics.density * dp + 0.5f).toInt()
    }


    /**
     * 依圆心坐标，半径，扇形角度，计算出扇形终射线与圆弧交叉点的xy坐标
     * 0为水平 270是正上方
     *
     * @param radius   半径
     * @param cirAngle 角度
     * @return x，y
     */
    @JvmStatic
    fun getCoordinatePoint(centerX: Float, centerY: Float, radius: Float, cirAngle: Float): FloatArray {
        val point = FloatArray(2)
        //将角度转换为弧度
        var arcAngle = Math.toRadians(cirAngle.toDouble())
        if (cirAngle < 90) {
            point[0] = (centerX + cos(arcAngle) * radius).toFloat()
            point[1] = (centerY + sin(arcAngle) * radius).toFloat()
        } else if (cirAngle == 90f) {
            point[0] = centerX
            point[1] = centerY + radius
        } else if (cirAngle > 90 && cirAngle < 180) {
            arcAngle = Math.PI * (180 - cirAngle) / 180.0
            point[0] = (centerX - cos(arcAngle) * radius).toFloat()
            point[1] = (centerY + sin(arcAngle) * radius).toFloat()
        } else if (cirAngle == 180f) {
            point[0] = centerX - radius
            point[1] = centerY
        } else if (cirAngle > 180 && cirAngle < 270) {
            arcAngle = Math.PI * (cirAngle - 180) / 180.0
            point[0] = (centerX - cos(arcAngle) * radius).toFloat()
            point[1] = (centerY - sin(arcAngle) * radius).toFloat()
        } else if (cirAngle == 270f) {
            point[0] = centerX
            point[1] = centerY - radius
        } else {
            arcAngle = Math.PI * (360 - cirAngle) / 180.0
            point[0] = (centerX + cos(arcAngle) * radius).toFloat()
            point[1] = (centerY - sin(arcAngle) * radius).toFloat()
        }
        return point
    }
}