package com.demon.demonnewest.module.flow

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.demonnewest.databinding.ActivityFlowBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.view.clicks

/**
 * @author DeMon
 * Created on 2022/5/31.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
class FlowActivity : MvvmActivity<ActivityFlowBinding, BaseViewModel>() {


    private val simpleFlow by lazy {
        flow {
            emit("1、flow1")
            emit("2、flow2")
            emit("3、flow3")
        }.flowOn(Dispatchers.IO)


        binding.btnFlow.clicks()
    }


    private val sharedFlow by lazy {
        flow {
            emit("1、flow")
            emit("2、convert")
            emit("3、SharedFlow")
        }.shareIn(lifecycleScope, SharingStarted.Eagerly, 1)
    }

    private val stateFlow by lazy {
        flow {
            emit("1、flow")
            emit("2、convert")
            emit("3、stateFlow")
        }.stateIn(lifecycleScope, SharingStarted.Eagerly, "0、initialValue")
    }

    override fun initData() {
        setToolbar("Flow")
        lifecycleScope.launch {
            simpleFlow.collect {
                Log.i(TAG, "simpleFlow: $it")
            }
            sharedFlow.collect {
                Log.i(TAG, "sharedFlow: $it")
            }
            stateFlow.collect {
                Log.i(TAG, "stateFlow: $it")
            }
        }


        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                simpleFlow.collect {
                    Log.i(TAG, "simpleFlow: $it")
                }
            }
        }

        binding.btnSharedFlow.setOnClickListener {
            lifecycleScope.launch {
                sharedFlow.collect {
                    Log.i(TAG, "sharedFlow: $it")
                }
            }
        }


        binding.btnStateFlow.setOnClickListener {
            lifecycleScope.launch {
                stateFlow.collect {
                    Log.i(TAG, "stateFlow: $it")
                }
            }
        }
    }
}