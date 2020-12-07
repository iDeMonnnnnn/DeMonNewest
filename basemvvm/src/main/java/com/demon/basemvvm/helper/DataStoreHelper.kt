package com.demon.basemvvm.helper

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.demon.basemvvm.MvvmApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import javax.inject.Singleton

/**
 * @author DeMon
 * Created on 2020/12/4.
 * E-mail 757454343@qq.com
 * Desc:
 */
@Singleton
class DataStoreHelper {
    companion object {
        const val TAG = "DataStoreHelper"
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            DataStoreHelper()
        }
    }

    val dataStore: DataStore<Preferences> = MvvmApp.instance.createDataStore(name = "DataStore")

    suspend inline fun <reified T : Any> get(key: String, default: T): T {
        val preferencesKey = preferencesKey<T>(key)
        var value: T? = null
       return dataStore.data.map {
            if (it[preferencesKey] is T) {
                value = it[preferencesKey]
            }
            value ?: default
        }.onEach {
            value = it
            Log.i(TAG, "get onEach:${preferencesKey.name}-$it")
        }.catch {
            it.printStackTrace()
        }.first()
    }

    inline fun <reified T : Any> put(key: String, value: T) {
        flow<Boolean> {
            val preferencesKey = preferencesKey<T>(key)
            dataStore.edit { preferences ->
                preferences[preferencesKey] = value
            }
        }.catch {
            it.printStackTrace()
        }.launchIn(GlobalScope)
    }

}

