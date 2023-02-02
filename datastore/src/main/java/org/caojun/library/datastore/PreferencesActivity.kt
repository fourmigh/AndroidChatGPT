package org.caojun.library.datastore

import android.content.Context
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
open class PreferencesActivity : AppCompatActivity() {

    fun saveDataStore(key: String, value: String) {
        lifecycleScope.launch {
            suspendSaveDataStore(key, value)
        }
    }
    suspend fun suspendSaveDataStore(key: String, value: String) {
        val spKey = stringPreferencesKey(key)
        dataStore.edit { settings ->
            settings[spKey] = value
        }
    }

    suspend fun suspendSaveDataStore(key: String, value: Int) {
        val ipKey = intPreferencesKey(key)
        dataStore.edit { settings ->
            settings[ipKey] = value
        }
    }

    fun loadDataStore(textView: TextView, key: String, defValue: String = "") {
        lifecycleScope.launch {
            suspendLoadDataStore(textView, key, defValue)
        }
    }
    suspend fun suspendLoadDataStore(textView: TextView, key: String, defValue: String = "") {
        val spKey = stringPreferencesKey(key)
        val flow = dataStore.data.map { preferences ->
            preferences[spKey] ?: defValue
        }
        flow.collect {
            withContext(Dispatchers.Main.immediate) {
                textView.text = it
            }
        }
    }
}