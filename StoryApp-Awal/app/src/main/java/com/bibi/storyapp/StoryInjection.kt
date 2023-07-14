package com.bibi.storyapp

import com.bibi.storyapp.data.api.ApiConfig
import com.bibi.storyapp.data.repository.StoryRepository

object StoryInjection {
    fun provideRepository(): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(apiService)
    }
}