package com.demon.demonjetpack.base.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.demon.basemvvm.utils.Tag
import com.demon.demonjetpack.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import javax.inject.Singleton

/**
 * @author DeMon
 * Created on 2020/12/4.
 * E-mail 757454343@qq.com
 * Desc:
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DataStore")

@Singleton
object DataStoreHelper {
    val coroutineScopeIO = CoroutineScope((SupervisorJob() + Dispatchers.IO))

    suspend inline fun <reified T : Any> get(key: String, default: T): T {
        return App.appContext.dataStore.data.map {
            val preferencesKey = when (default) {
                is Int -> it[intPreferencesKey(key)] ?: default
                is Long -> it[longPreferencesKey(key)] ?: default
                is String -> it[stringPreferencesKey(key)] ?: default
                is Boolean -> it[booleanPreferencesKey(key)] ?: default
                is Float -> it[floatPreferencesKey(key)] ?: default
                is Double -> it[doublePreferencesKey(key)] ?: default
                else -> throw IllegalArgumentException("This type of $default error")
            }
            preferencesKey
        }.onEach {
            Log.i(Tag, "get onEach:${key}-$it")
        }.catch {
            it.printStackTrace()
        }.first() as T
    }

    inline fun <reified T : Any> put(key: String, value: T) {
        flow<Boolean> {
            App.appContext.dataStore.edit { preferences ->
                when (value) {
                    is Int -> preferences[intPreferencesKey(key)] = (value as Int)
                    is Long -> preferences[longPreferencesKey(key)] = (value as Long)
                    is String -> preferences[stringPreferencesKey(key)] = (value as String)
                    is Boolean -> preferences[booleanPreferencesKey(key)] = (value as Boolean)
                    is Float -> preferences[floatPreferencesKey(key)] = (value as Float)
                    is Double -> preferences[doublePreferencesKey(key)] = (value as Double)
                    else -> throw IllegalArgumentException("This type of $value error!")
                }
            }
        }.catch {
            it.printStackTrace()

        }.launchIn(coroutineScopeIO)
    }


}

