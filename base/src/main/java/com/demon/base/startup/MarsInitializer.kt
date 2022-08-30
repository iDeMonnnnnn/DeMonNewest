package com.demon.base.startup

import android.content.Context
import androidx.startup.Initializer
import com.demon.base.utils.SystemUtils
import com.demon.base.utils.ext.Tag
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.MarsXLog

/**``
 * @author DeMon
 * Created on 2022/3/29.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class MarsInitializer : Initializer<String> {

    override fun create(context: Context): String {
        val nameprefix = "Xlog_" + SystemUtils.getCurrentProcess(context)
        MarsXLog.initXlog(context, nameprefix)
        return Tag
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}