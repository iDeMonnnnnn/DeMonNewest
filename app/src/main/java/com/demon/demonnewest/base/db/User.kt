package com.demon.demonnewest.base.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author DeMon
 * Created on 2020/1/18.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
@Entity(tableName = "users")
class User constructor(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String = "",
    var sex: String = "无",
    var age: Int = 0,
    var updateTime: Long = System.currentTimeMillis()
) {
    override fun toString(): String {
        return "User(id=$id, name='$name', sex='$sex', age=$age, updateTime=$updateTime)"
    }
}