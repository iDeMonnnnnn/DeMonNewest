package com.demon.easyjetpack.module.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.easyjetpack.ext.getDataOrThrow
import com.demon.easyjetpack.http.DataRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
class FragmentViewModel @Inject constructor(@JvmField @Inject var repository: DataRepository) : BaseViewModel() {

    val weatherData = MutableLiveData<String>()

    fun getWeather(loc: String) {
        viewModelScope.launch {
            runCatching {
                val result = repository.getNowWeather(loc).getDataOrThrow(mContext)
                weatherData.value = result.getKeyData("now")
            }.onFailure {
                errLiveData.value = it.message
            }
        }
    }
}