package com.demon.demonjetpack.module.datastore

import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.basemvvm.utils.launchUI
import com.demon.basemvvm.utils.mmkv
import com.demon.basemvvm.utils.setOnClickThrottleFirst
import com.demon.demonjetpack.base.data.RouterConst
import com.demon.demonjetpack.base.ext.dsGet
import com.demon.demonjetpack.base.ext.dsPut
import com.demon.demonjetpack.databinding.ActivityDataStoreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

/**
 * @author DeMon
 * Created on 2021/10/22.
 * E-mail 757454343@qq.com
 * Desc:
 */
@Route(path = RouterConst.ACT_DATASTORE)
@AndroidEntryPoint
class DataStoreActivity : MvvmActivity<ActivityDataStoreBinding, BaseViewModel>() {
    override fun initData() {
        setToolbar("DataStore VS MMKV")

        var string = ""
        var long = 0L
        var boolean = false
        bindingRun {
            btnRefresh.setOnClickThrottleFirst {
                string = "${System.currentTimeMillis()}"
                long = System.currentTimeMillis()
                boolean = Random.nextInt(2) == 1
                tvContent.text = "key_String=${string}\n" +
                        "key_Long=${long}\n" +
                        "key_Boolean=${boolean}"
            }

            btnPut.setOnClickThrottleFirst {
                mmkv?.encode("key_String", string)
                mmkv?.encode("key_Long", long)
                mmkv?.encode("key_Boolean", boolean)
                dsPut("key_String", string)
                dsPut("key_Long", long)
                dsPut("key_Boolean", boolean)
            }

            btnGet.setOnClickThrottleFirst {
                tvMVVK.text = "MVVK:\n" +
                        "key_String=${mmkv?.decodeString("key_String")}\n" +
                        "key_Long=${mmkv?.decodeLong("key_Long")}\n" +
                        "key_Boolean=${mmkv?.decodeBool("key_Boolean")}"
                launchUI {
                    tvDataStore.text = "DataStore:\n" +
                            "key_String=${dsGet("key_String", "")}\n" +
                            "key_Long=${dsGet("key_Long", 0L)}\n" +
                            "key_Boolean=${dsGet("key_Boolean", false)}"
                }

            }
        }
    }

}