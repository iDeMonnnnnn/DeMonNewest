package com.demon.basemvvm.intent

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.demon.basemvvm.ara.DeMonActivityCallbacks
import com.demon.basemvvm.ara.DeMonActivityResult
import com.demon.basemvvm.utils.pairIntent

/**
 * @author DeMon
 * Created on 2021/10/20.
 * E-mail idemon_liu@qq.com
 * Desc: ktx扩展
 */

/**
 * Activity中获取DeMonActivityResult
 */
fun FragmentActivity.getActivityResult(): DeMonActivityResult<Intent, ActivityResult>? {
    val mapKey = intent.getStringExtra(DeMonActivityCallbacks.DEMON_ACTIVITY_KEY)
    return if (!mapKey.isNullOrEmpty()) {
        DeMonActivityCallbacks.resultMap[mapKey]
    } else {
        null
    }
}

/**
 * Fragment中获取DeMonActivityResult
 */
fun Fragment.getActivityResult(): DeMonActivityResult<Intent, ActivityResult>? {
    val mapKey = requireActivity().intent.getStringExtra(DeMonActivityCallbacks.DEMON_FRAGMENT_KEY)
    return if (!mapKey.isNullOrEmpty()) {
        DeMonActivityCallbacks.resultMap[mapKey]
    } else {
        null
    }
}

/**
 * Activity跳转并在回调用获取返回结果
 *  <pre>
 *       val intent = Intent(this@MainActivity,JavaActivity::class.java)
 *       forActivityResult(intent) {
 *       val str = it?.getStringExtra("tag") ?: ""
 *       text.text = "跳转页面返回值：$str"
 *       }
 *  </pre>
 *
 * @param isCanBack 直接点击返回键或者直接finish是否会触发回调
 */
inline fun FragmentActivity.forActivityResult(
    data: Intent,
    isCanBack: Boolean = false,
    crossinline callback: ((result: Intent?) -> Unit)
) {
    getActivityResult()?.launch(data, isCanBack) {
        callback(it.data)
    }
}

/**
 * Activity跳转并在回调用获取返回结果
 *  <pre>
 *       forActivityResult<TestJumpActivity>(
 *       "tag" to TAG,
 *       "timestamp" to System.currentTimeMillis(),
 *       isCanBack = false
 *      ) {
 *      val str = it?.getStringExtra("tag") ?: ""
 *      text.text = "跳转页面返回值：$str"
 *      }
 *  </pre>
 *
 * @param extras 可变参数Pair键值对
 * @param isCanBack 直接点击返回键或者直接finish是否会触发回调
 */
inline fun <reified T : FragmentActivity> FragmentActivity.forActivityResult(
    vararg extras: Pair<String, Any?>,
    isCanBack: Boolean = false,
    crossinline callback: ((result: Intent?) -> Unit)
) {
    val intent = pairIntent<T>(*extras)
    forActivityResult(intent, isCanBack, callback)
}

/**
 * Fragment中使用
 * Activity跳转并在回调用获取返回结果
 *
 * @param isCanBack 直接点击返回键或者直接finish是否会触发回调
 */
inline fun Fragment.forActivityResult(
    data: Intent,
    isCanBack: Boolean = false,
    crossinline callback: ((result: Intent?) -> Unit)
) {
    getActivityResult()?.launch(data, isCanBack) {
        callback(it.data)
    }
}

/**
 * Fragment中使用
 * Activity跳转并在回调用获取返回结果
 *
 * @param extras 可变参数Pair键值对
 * @param isCanBack 直接点击返回键或者直接finish是否会触发回调
 */
inline fun <reified T : FragmentActivity> Fragment.forActivityResult(
    vararg extras: Pair<String, Any?>,
    isCanBack: Boolean = false,
    crossinline callback: ((result: Intent?) -> Unit)
) {
    val intent = pairIntent<T>(*extras)
    forActivityResult(intent, isCanBack, callback)
}



