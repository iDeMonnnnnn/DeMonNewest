package com.demon.easyjetpack.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author DeMon
 * Created on 2020/1/18.
 * E-mail 757454343@qq.com
 * Desc:
 */
@Entity(tableName = "users")
class User @JvmOverloads constructor(
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