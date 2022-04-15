package com.demon.demonnewest.module.paging

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * @author DeMon
 * Created on 2020/12/17.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class DiffComparator<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}