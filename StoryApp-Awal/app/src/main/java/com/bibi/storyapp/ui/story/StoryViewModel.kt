package com.bibi.storyapp.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bibi.storyapp.data.preferences.UserPreferences
import com.bibi.storyapp.data.repository.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(
    private val storyRepo: StoryRepository,
    private val userPref: UserPreferences
) :
    ViewModel() {

    fun getToken(): LiveData<String> {
        return userPref.getToken().asLiveData()
    }

    fun addStory(token: String, imageMultipart: MultipartBody.Part, desc: RequestBody) =
        storyRepo.addStory(token, imageMultipart, desc)

}