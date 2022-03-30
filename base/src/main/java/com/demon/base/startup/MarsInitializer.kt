package com.demon.base.startup

import android.content.Context
import androidx.startup.Initializer
import com.demon.base.utils.SystemUtils
import com.demon.base.utils.ext.Tag
import com.tencent.mars.xlog.Log

/**``
 * @author DeMon
 * Created on 2022/3/29.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class MarsInitializer : Initializer<String> {

    override fun create(context: Context): String {
        val nameprefix = "Xlog_" + SystemUtils.getCurrentProcess(context)
        val pubkey = "f80a741f64d9d4261548b2e51059ac42a9419f7f8d3d2bc1bd0e7d4564122e1fdb2d2706c0b2b89bbbd220abb15b1b585de0f3484a404793bed4a7acb2101b4f"
        Log.initXlog(context, nameprefix, pubkey)
        return Tag
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}