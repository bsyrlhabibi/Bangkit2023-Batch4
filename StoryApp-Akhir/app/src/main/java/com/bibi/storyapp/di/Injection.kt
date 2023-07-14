package com.bibi.storyapp.di

import android.content.Context
import com.bibi.storyapp.data.paging.StoryRepository
import com.bibi.storyapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService)
    }
}