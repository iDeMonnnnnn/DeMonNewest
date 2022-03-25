package com.demon.base.ghsot

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.random.Random

/**
 * @author DeMon
 * Created on 2022/3/4.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
suspend inline fun <reified T : FragmentActivity> Fragment.launchActivityForResult(
    isCanBack: Boolean = false,
    requestCode: Int = Random.nextInt(1, 1000)
): Intent? {
    return suspendCancellableCoroutine { continuation ->
        activityForResult(
            Intent(requireActivity(), T::class.java),
            isCanBack,
            requestCode
        ) { data ->
            continuation.resumeWith(Result.success(data))
        }
    }
}

suspend inline fun <reified T : FragmentActivity> FragmentActivity.launchActivityForResult(
    isCanBack: Boolean = false,
    requestCode: Int = Random.nextInt(1, 1000)
): Intent? {
    return suspendCancellableCoroutine { continuation ->
        activityForResult(
            Intent(this, T::class.java),
            isCanBack,
            requestCode
        ) { data ->
            continuation.resumeWith(Result.success(data))
        }
    }
}


suspend inline fun Fragment.launchActivityForResult(
    intent: Intent,
    isCanBack: Boolean = false,
    requestCode: Int = Random.nextInt(1, 1000)
): Intent? {
    return suspendCancellableCoroutine { continuation ->
        activityForResult(
            intent,
            isCanBack,
            requestCode
        ) { data ->
            continuation.resumeWith(Result.success(data))
        }
    }
}


suspend inline fun FragmentActivity.launchActivityForResult(
    intent: Intent,
    isCanBack: Boolean = false,
    requestCode: Int = Random.nextInt(1, 1000)
): Intent? {
    return suspendCancellableCoroutine { continuation ->
        activityForResult(
            intent,
            isCanBack,
            requestCode,
        ) { data ->
            continuation.resumeWith(Result.success(data))
        }
    }
}

inline fun Fragment.activityForResult(
    intent: Intent,
    isCanBack: Boolean = false,
    requestCode: Int = Random.nextInt(1, 1000),
    crossinline callback: ((result: Intent?) -> Unit)
) {
    val fragment = GhostFragment()
    fragment.init(requestCode, intent, isCanBack) { result ->
        callback(result)
        childFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
    }
    childFragmentManager.beginTransaction().add(fragment, GhostFragment::class.java.simpleName)
        .commitAllowingStateLoss()
}

inline fun FragmentActivity.activityForResult(
    intent: Intent,
    isCanBack: Boolean = false,
    requestCode: Int = Random.nextInt(1, 1000),
    crossinline callback: ((result: Intent?) -> Unit)
) {
    val fragment = GhostFragment()
    fragment.init(requestCode, intent, isCanBack) { result ->
        callback(result)
        supportFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
    }
    supportFragmentManager.beginTransaction().add(fragment, GhostFragment::class.java.simpleName)
        .commitAllowingStateLoss()
}

class GhostFragment : Fragment() {

    private var isCanBack = false //返回时是否触发回调，默认false
    private var requestCode = -1
    private var intent: Intent? = null
    private var callback: ((result: Intent?) -> Unit)? = null


    fun init(intent: Intent, callback: ((result: Intent?) -> Unit)) {
        this.intent = intent
        this.callback = callback
    }


    fun init(requestCode: Int, intent: Intent, callback: ((result: Intent?) -> Unit)) {
        this.requestCode = requestCode
        this.intent = intent
        this.callback = callback
    }

    fun init(requestCode: Int, intent: Intent, isCanBack: Boolean, callback: ((result: Intent?) -> Unit)) {
        this.requestCode = requestCode
        this.intent = intent
        this.callback = callback
        this.isCanBack = isCanBack
    }

    //https://github.com/wuyr/ActivityMessenger/issues/6
    private var activityStarted = false

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (!activityStarted) {
            activityStarted = true
            intent?.let { startActivityForResult(it, requestCode) }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!activityStarted) {
            activityStarted = true
            intent?.let { startActivityForResult(it, requestCode) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (isCanBack && requestCode == this.requestCode) {
            callback?.let { it(data) }
        } else if (resultCode == Activity.RESULT_OK && requestCode == this.requestCode) {
            callback?.let { it(data) }
        }
    }

    override fun onDetach() {
        super.onDetach()
        intent = null
        callback = null
    }
}