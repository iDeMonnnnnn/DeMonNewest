package com.demon.easyjetpack.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author DeMon
 * Created on 2020/1/18.
 * E-mail 757454343@qq.com
 * Desc:
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}