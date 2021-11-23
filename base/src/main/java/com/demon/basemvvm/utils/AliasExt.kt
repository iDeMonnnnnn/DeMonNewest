package com.demon.basemvvm.utils

import com.tencent.mmkv.MMKV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * @author DeMon
 * Created on 2021/11/22.
 * E-mail 757454343@qq.com
 * Desc: 别名
 */
var mmkv = MMKV.defaultMMKV()


val coroutineScopeIO = CoroutineScope((SupervisorJob() + Dispatchers.IO))