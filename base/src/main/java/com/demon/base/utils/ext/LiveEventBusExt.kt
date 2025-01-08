package com.color.book.util.ext

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.LiveEvent
import com.jeremyliao.liveeventbus.core.LiveEventBusCore
import com.jeremyliao.liveeventbus.core.Observable

/**
 * @author DeMon
 * Created on 2025/1/8.
 * E-mail demon.l@quest-tech.ai
 * Desc:
 */
inline fun <reified V> postLiveBus(
    key: String,
    value: V
) {
    LiveEventBus.get<V>(key).post(value)
}

inline fun <reified V> V.postLiveBus(
    key: String,
) {
    LiveEventBus.get<V>(key).post(this)
}

/**
 * 直接发送数据类，需要继承[LiveEvent]接口
 */
inline fun <reified V : LiveEvent> V.postLiveBus() {
    LiveEventBus.get(V::class.java).post(this)
}

fun String.postStringBus() {
    LiveEventBus.get<String>(this).post("")
}

inline fun LifecycleOwner.liveStringBus(
    key: String,
    crossinline onChanged: (String) -> Unit
): Observer<String> {
    return LiveEventBusCore.get()
        .with(key, String::class.java)
        .observer(this, onChanged)
}

inline fun <reified V: LiveEvent> V.postProcessLiveBus() {
    LiveEventBus.get(V::class.java).postAcrossProcess(this)
}

inline fun <reified T> LifecycleOwner.liveBus(
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    return LiveEventBusCore.get()
        .with(T::class.java.name, T::class.java)
        .observer(this, onChanged)
}

inline fun <reified T> LifecycleOwner.liveBusSticky(
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    return LiveEventBusCore.get()
        .with(T::class.java.name, T::class.java)
        .observerSticky(this, onChanged)
}

inline fun <reified T> LifecycleOwner.liveBus(
    key: String,
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    return LiveEventBusCore.get()
        .with(key, T::class.java)
        .observer(this, onChanged)
}

inline fun <reified T: LiveEvent> registerLiveBus(
    observer: Observer<T>
) {
    return LiveEventBusCore.get()
        .with(T::class.java.name, T::class.java)
        .observeForever(observer)
}

inline fun <reified T: LiveEvent> unregisterLiveBus(
    observer: Observer<T> = Observer<T> { }
) {
    return LiveEventBusCore.get()
        .with(T::class.java.name, T::class.java)
        .removeObserver(observer)
}

inline fun <reified T> registerLiveBus(
    key: String,
    observer: Observer<T>
) {
    return LiveEventBusCore.get()
        .with(key, T::class.java)
        .observeForever(observer)
}

inline fun <reified T> unregisterLiveBus(
    key: String,
    observer: Observer<T>
) {
    return LiveEventBusCore.get()
        .with(key, T::class.java)
        .removeObserver(observer)
}

@MainThread
inline fun <T> Observable<T>.observer(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    val wrappedObserver = Observer<T> { t -> onChanged.invoke(t) }
    observe(owner, wrappedObserver)
    return wrappedObserver
}

@MainThread
inline fun <T> Observable<T>.observerSticky(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    val wrappedObserver = Observer<T> { t -> onChanged.invoke(t) }
    observeSticky(owner, wrappedObserver)
    return wrappedObserver
}

