package com.bibi.storyapp.viewmodel

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bibi.storyapp.data.paging.StoryRepository
import com.bibi.storyapp.data.remote.response.ListStory
import com.bibi.storyapp.di.Injection

class HomeViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getAllStory(token: String) : LiveData<PagingData<ListStory>> {
        return storyRepository.getStory(token).cachedIn(viewModelScope)
    }
}

class ViewModelFactoryPaging(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}