package com.bibi.storyapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bibi.storyapp.data.preferences.UserPreferences
import com.bibi.storyapp.data.repository.StoryRepository
import com.bibi.storyapp.ui.main.MainViewModel
import com.bibi.storyapp.ui.story.StoryViewModel

class StoryViewModelFactory private constructor(
    private val storyRepo: StoryRepository,
    private val sessionPref: UserPreferences
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(storyRepo, sessionPref) as T
            }
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(storyRepo, sessionPref) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: StoryViewModelFactory? = null
        fun getInstance(context: Context): StoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: StoryViewModelFactory(
                    StoryInjection.provideRepository(),
                    UserInjection.providePreferences(context)
                )
            }.also { instance = it }
    }

}