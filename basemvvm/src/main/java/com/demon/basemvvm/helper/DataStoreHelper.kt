package com.demon.basemvvm.helper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.demon.basemvvm.MvvmApp
import com.demon.basemvvm.utils.launchIO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.onEach
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
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            DataStoreHelper()
        }
    }

    val dataStore: DataStore<Preferences> = MvvmApp.instance.createDataStore(name = "DataStore")


    inline fun <reified T : Any> get(key: String, default: T): T {
        val preferencesKey = preferencesKey<T>(key)
        var value: T? = null
        dataStore.data.onEach {
            value = it[preferencesKey]
        }
        return value ?: default
    }

    inline fun <reified T : Any> put(key: String, value: T) {
        GlobalScope.launchIO {
            val preferencesKey = preferencesKey<T>(key)
            dataStore.edit { preferences ->
                preferences[preferencesKey] = value
            }
        }
    }


}