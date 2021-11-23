package com.demon.basemvvm.utils

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * @author DeMon
 * Created on 2020/11/25.
 * E-mail 757454343@qq.com
 * Desc:
 */

/**
 * 获取当前类的TAG
 */
inline val <T : Any> T.Tag: String
    get() = this.javaClass.simpleName



fun Context.dpToPx(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f * if (dp >= 0) 1 else -1).toInt()
}

/**
 * io线程
 */
fun CoroutineScope.launchIO(block: suspend () -> Unit): Job {
    return this.launch(Dispatchers.IO) { block() }
}

/**
 * 主线程
 */
fun CoroutineScope.launchUI(block: suspend () -> Unit): Job {
    return this.launch(Dispatchers.Main) { block() }
}

fun LifecycleOwner.launch(block: suspend () -> Unit): Job {
    return lifecycleScope.launch { block() }
}

fun LifecycleOwner.launchUI(block: suspend () -> Unit): Job {
    return lifecycleScope.launch(Dispatchers.Main) { block() }
}

fun LifecycleOwner.launchIO(block: suspend () -> Unit): Job {
    return lifecycleScope.launch(Dispatchers.IO) { block() }
}

fun ViewModel.launch(block: suspend () -> Unit): Job {
    return viewModelScope.launch { block() }
}

fun ViewModel.launchUI(block: suspend () -> Unit): Job {
    return viewModelScope.launch(Dispatchers.Main) { block() }
}

fun ViewModel.launchIO(block: suspend () -> Unit): Job {
    return viewModelScope.launch(Dispatchers.IO) { block() }
}