package com.example.florys.dependencyInjection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.florys.data.api.ApiConfig
import com.example.florys.data.pref.UserPreference
import com.example.florys.data.repository.Repository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
object Injection {
    fun provideRepository(context: Context): Repository {
        val dataStore = context.dataStore
        val pref = UserPreference.getInstance(dataStore)
        val apiService = ApiConfig().getApiService()
        return Repository(apiService, pref)
    }
}