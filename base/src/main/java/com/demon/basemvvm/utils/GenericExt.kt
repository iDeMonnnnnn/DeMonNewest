package com.demon.basemvvm.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.GenericSignatureFormatError
import java.lang.reflect.MalformedParameterizedTypeException
import java.lang.reflect.ParameterizedType

/**
 * @author DeMon
 * Created on 2021/11/22.
 * E-mail 757454343@qq.com
 * Desc: 泛型相关方法
 */
/**
 * 获取泛型类的Class类型
 * @param index,表示第几个泛型
 */
inline fun <T : Any> Any.getTClass(index: Int = 0): Class<T> = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<T>

/**
 * 反射执行ViewBinding的inflate静态方法，主要在Activity中使用
 *
 * @param inflater LayoutInflater参数
 * @param index 表示第几个泛型
 */
inline fun <VB : ViewBinding> Any.inflateViewBinding(inflater: LayoutInflater, index: Int = 0): VB {
    val cla = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<VB>
    return cla.getMethod("inflate", LayoutInflater::class.java).invoke(null, inflater) as VB
}

/**
 * 反射执行ViewBinding的inflate静态方法，主要在Fragment中使用
 *
 * @param inflater LayoutInflater参数
 * @param container ViewGroup
 * @param index 表示第几个泛型
 */
inline fun <VB : ViewBinding> Any.inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, index: Int = 0): VB {
    val cla = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<VB>
    return cla.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        .invoke(null, inflater, container, false) as VB
}

/**
 * 反射执行ViewBinding的inflate静态方法,主要在DataViewHolder中使用
 * @param container ViewGroup
 * @param index 表示第几个泛型
 */
inline fun <VB : ViewBinding> Any.inflateViewBinding(container: ViewGroup, index: Int = 0): VB {
    val cla = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<VB>
    return cla.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        .invoke(null, LayoutInflater.from(container.context), container, false) as VB
}


/**
 * 在泛型类自己及父类中寻找某个类
 */
inline fun Any.findTClass(claz: Class<*>): Class<*>? {
    var temp: Class<*>? = javaClass
    var z: Class<*>? = null
    while (z == null && null != temp) {
        z = getInstancedGenericKClass(temp, claz)
        temp = temp.superclass
    }
    return z
}

fun getInstancedGenericKClass(z: Class<*>, claz: Class<*>): Class<*>? {
    try {
        val type = z.genericSuperclass
        if (type is ParameterizedType) {
            val types = type.actualTypeArguments
            for (temp in types) {
                if (temp is Class<*>) {
                    if (claz.isAssignableFrom(temp)) {
                        return temp
                    }
                } else if (temp is ParameterizedType) {
                    val rawType = temp.rawType
                    if (rawType is Class<*> && claz.isAssignableFrom(rawType)) {
                        return rawType
                    }
                }
            }
        }
    } catch (e: GenericSignatureFormatError) {
        e.printStackTrace()
    } catch (e: TypeNotPresentException) {
        e.printStackTrace()
    } catch (e: MalformedParameterizedTypeException) {
        e.printStackTrace()
    }
    return null
}