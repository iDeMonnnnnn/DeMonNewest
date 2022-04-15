package com.demon.demonnewest.module.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.demon.base.mvvm.BaseViewModel
import com.demon.demonnewest.base.db.User
import com.demon.demonnewest.base.db.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

/**
 * @author DeMon
 * Created on 2020/1/18.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
@HiltViewModel
class RoomViewModel @Inject constructor(var dao: UserDao) : BaseViewModel() {


    val useData: LiveData<List<User>>
        get() = getUsers()

    val errorStr = MediatorLiveData<String>()

    fun getUsers() = dao.observeUsers()


    fun insertUser(name: String) {
        val sex = if (Random.nextInt(2) == 0) {
            "男"
        } else {
            "女"
        }
        viewModelScope.launch {
            dao.insertUser(User(name = name, sex = sex, age = Random.nextInt(100)))

            getUsers()
        }
    }


    fun deleteUser(id: Int) {
        viewModelScope.launch {
            if (dao.deleteUserByUid(id) < 1) {
                errorStr.value = "删除失败"
            } else {
                getUsers()
            }
        }
    }


    fun updateUser(id: Int, name: String) {
        viewModelScope.launch {
            if (dao.updateUser(User(id, name)) == 0) {
                errorStr.value = "更新失败"
            } else {
                getUsers()
            }

        }
    }
}