package com.demon.basemvvm.intent

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * @author DeMon
 * Created on 2020/7/22.
 * E-mail 757454343@qq.com
 * Desc:
 */
object ActivityMessenger {
    var sRequestCode = 0
        set(value) {
            field = if (value >= Integer.MAX_VALUE) 1 else value
        }


    inline fun startActivityForResult(
        starter: FragmentActivity?, intent: Intent, crossinline callback: ((result: Intent?) -> Unit)
    ) = starter?.let {
        val fragment = GhostFragment()
        fragment.init(++sRequestCode, intent) { result ->
            callback(result)
            it.supportFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
        }
        it.supportFragmentManager.beginTransaction().add(fragment, GhostFragment::class.java.simpleName).commitAllowingStateLoss()
    }

    inline fun startActivityForResult(starter: FragmentActivity?, cls: Class<*>, crossinline callback: ((result: Intent?) -> Unit)) =
        starter?.let { startActivityForResult(it, Intent(it, cls), callback) }

    inline fun startActivityForResult(starter: FragmentActivity?, cls: Class<*>, vararg params: Pair<String, Any?>, crossinline callback: ((result: Intent?) -> Unit)) =
        starter?.let { startActivityForResult(it, Intent(it, cls).putExtras(*params), callback) }
}

inline fun FragmentActivity.toActivityForResult(
    cls: Class<*>, vararg params: Pair<String, Any?>, crossinline callback: ((result: Intent?) -> Unit)
) = ActivityMessenger.startActivityForResult(this, cls, *params, callback = callback)

inline fun FragmentActivity.toActivityForResult(
    cls: Class<*>, crossinline callback: ((result: Intent?) -> Unit)
) = ActivityMessenger.startActivityForResult(this, cls, callback = callback)

inline fun Fragment.toActivityForResult(cls: Class<*>, crossinline callback: ((result: Intent?) -> Unit)) = activity?.run {
    ActivityMessenger.startActivityForResult(this, cls, callback = callback)
}

inline fun Fragment.toActivityForResult(cls: Class<*>, vararg params: Pair<String, Any?>, crossinline callback: ((result: Intent?) -> Unit)) = activity?.run {
    ActivityMessenger.startActivityForResult(this, cls, *params, callback = callback)
}

/**
 *  作用同[Activity.finish]
 *  示例：
 *  <pre>
 *      finish(this, "Key" to "Value")
 *  </pre>
 *
 * @param params extras键值对
 */
fun FragmentActivity.finishResult(vararg params: Pair<String, Any?>) = run {
    setResult(Activity.RESULT_OK, Intent().putExtras(*params))
    finish()
}

fun FragmentActivity.finishResult(intent: Intent) = run {
    setResult(Activity.RESULT_OK, intent)
    finish()
}
