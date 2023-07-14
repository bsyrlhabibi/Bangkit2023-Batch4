package com.bibi.storyapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bibi.storyapp.data.api.ApiConfig
import com.bibi.storyapp.data.preferences.UserPreferences
import com.bibi.storyapp.data.repository.UserRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object UserInjection {

    fun provideRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }

    fun providePreferences(context: Context): UserPreferences {
        return UserPreferences.getInstance(context.dataStore)
    }

}