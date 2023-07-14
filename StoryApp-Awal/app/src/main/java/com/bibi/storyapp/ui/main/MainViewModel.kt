package com.bibi.storyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bibi.storyapp.data.preferences.UserPreferences
import com.bibi.storyapp.data.repository.StoryRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val storyRepo: StoryRepository,
    private val userPref: UserPreferences
) : ViewModel() {

    fun getToken(): LiveData<String> {
        return userPref.getToken().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userPref.logout()
        }
    }

    fun getStories(token: String) = storyRepo.getStories(token)

}