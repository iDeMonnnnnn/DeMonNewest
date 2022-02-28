package com.demon.basemvvm.intent

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * @author DeMon
 * Created on 2021/10/20.
 * E-mail 757454343@qq.com
 * Desc: Activity Results API
 */
class PairActivityResultContract constructor(private val cls: Class<*>) : ActivityResultContract<Array<Pair<String, Any?>>, ActivityResult>() {

    override fun createIntent(context: Context, input: Array<Pair<String, Any?>>): Intent {
        val intent = Intent(context, cls)
        intent.putExtras(*input)
        return intent
    }

    override fun parseResult(resultCode: Int, data: Intent?): ActivityResult {
        return ActivityResult(resultCode, data)
    }

}

/**
 * 回调执行
 */
inline fun <reified T : FragmentActivity> FragmentActivity.forActivityResult(
    vararg extras: Pair<String, Any?>,
    isCanBack: Boolean = false,
    crossinline callback: ((result: Intent?) -> Unit)
) =
    registerForActivityResult(PairActivityResultContract(T::class.java)) {
        if (isCanBack || it.resultCode == Activity.RESULT_OK) {
            callback(it.data)
        }
    }.launch(arrayOf(*extras))


inline fun <reified T : FragmentActivity> Fragment.forActivityResult(
    vararg extras: Pair<String, Any?>,
    isCanBack: Boolean = false,
    crossinline callback: ((result: Intent?) -> Unit)
) =
    registerForActivityResult(PairActivityResultContract(T::class.java)) {
        if (isCanBack || it.resultCode == Activity.RESULT_OK) {
            callback(it.data)
        }
    }.launch(arrayOf(*extras))


inline fun AppCompatActivity.forActivityResult(
    intent: Intent,
    isCanBack: Boolean = false,
    crossinline callback: ((result: Intent?) -> Unit)
) =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (isCanBack || it.resultCode == Activity.RESULT_OK) {
            callback(it.data)
        }
    }.launch(intent)


inline fun Fragment.forActivityResult(
    intent: Intent,
    isCanBack: Boolean = false,
    crossinline callback: ((result: Intent?) -> Unit)
) =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (isCanBack || it.resultCode == Activity.RESULT_OK) {
            callback(it.data)
        }
    }.launch(intent)

/**
 * 携程执行
 */
suspend inline fun <reified T : FragmentActivity> FragmentActivity.forActivityResult(vararg extras: Pair<String, Any?>): Intent? {
    return suspendCancellableCoroutine { continuation ->
        registerForActivityResult(PairActivityResultContract(T::class.java)) {
            continuation.resumeWith(Result.success(it.data))
        }.launch(arrayOf(*extras))
    }
}

suspend inline fun FragmentActivity.launchForActivityResult(intent: Intent): Intent? {
    return suspendCancellableCoroutine { continuation ->
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            continuation.resumeWith(Result.success(it.data))
        }.launch(intent)
    }
}

suspend inline fun <reified T : FragmentActivity> Fragment.forActivityResult(vararg extras: Pair<String, Any?>): Intent? {
    return suspendCancellableCoroutine { continuation ->
        registerForActivityResult(PairActivityResultContract(T::class.java)) {
            continuation.resumeWith(Result.success(it.data))
        }.launch(arrayOf(*extras))
    }
}

suspend inline fun Fragment.launchForActivityResult(intent: Intent): Intent? {
    return suspendCancellableCoroutine { continuation ->
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            continuation.resumeWith(Result.success(it.data))
        }.launch(intent)
    }
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

/**
 * 普通跳转
 */
fun Context.toActivity(intent: Intent, vararg extras: Pair<String, Any?>) {
    startActivity(
        if (this == Application().applicationContext) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(*extras)
        } else {
            intent.putExtras(*extras)
        }
    )
}

fun Fragment.toActivity(intent: Intent, vararg extras: Pair<String, Any?>) {
    activity?.run {
        startActivity(intent.putExtras(*extras))
    }
}


fun Context.toActivity(cls: Class<*>, vararg extras: Pair<String, Any?>) {
    startActivity(
        if (this == Application().applicationContext) {
            Intent(this, cls).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(*extras)
        } else {
            Intent(this, cls).putExtras(*extras)
        }
    )
}

fun Fragment.toActivity(cls: Class<*>, vararg extras: Pair<String, Any?>) {
    activity?.run {
        startActivity(Intent(this, cls).putExtras(*extras))
    }
}

