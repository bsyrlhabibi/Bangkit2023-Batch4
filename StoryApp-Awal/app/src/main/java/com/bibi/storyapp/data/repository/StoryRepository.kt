package com.bibi.storyapp.data.repository

import com.bibi.storyapp.data.model.Result
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bibi.storyapp.data.api.ApiService
import com.bibi.storyapp.data.response.AddStoryResponse
import com.bibi.storyapp.data.response.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

class StoryRepository(private val apiService: ApiService) {

    fun getStories(token: String): LiveData<Result<StoriesResponse>> = liveData {
        emit(Result.Loading)
        try {
            val stories = apiService.getStories("Bearer $token")
            emit(Result.Success(stories))
        } catch (e: Exception) {
            Log.d(TAG, "getStories: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addStory(
        token: String,
        imageMultipart: MultipartBody.Part,
        desc: RequestBody,
    ): LiveData<Result<AddStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val newStory = apiService.addStory("Bearer $token", imageMultipart, desc)
            emit(Result.Success(newStory))
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "addStory: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        private val TAG = StoryRepository::class.java.simpleName

        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService)
            }.also { instance = it }
    }
}