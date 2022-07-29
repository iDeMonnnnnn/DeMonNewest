package com.demon.demonnewest.module.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.utils.ext.launchUI
import com.demon.demonnewest.base.db.User
import com.demon.demonnewest.base.db.UserDao
import com.demon.demonnewest.base.db.UserDaoAsyn
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
class RoomViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var dao: UserDao

    @Inject
    lateinit var daoAsyn: UserDaoAsyn

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
            daoAsyn.insertUser(User(name = name, sex = sex, age = Random.nextInt(100)))
            getUsers()
        }
    }


    fun deleteUser(id: Int) {
        viewModelScope.launch {
            if (daoAsyn.deleteUserByUid(id) < 1) {
                errorStr.value = "删除失败"
            } else {
                getUsers()
            }
        }
    }


    fun updateUser(id: Int, name: String) {
        viewModelScope.launch {
            if (daoAsyn.updateUser(User(id, name)) == 0) {
                errorStr.value = "更新失败"
            } else {
                getUsers()
            }

        }
    }
}