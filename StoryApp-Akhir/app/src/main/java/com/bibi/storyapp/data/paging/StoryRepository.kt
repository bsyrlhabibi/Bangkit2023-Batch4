package com.bibi.storyapp.data.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.bibi.storyapp.data.remote.response.ListStory
import com.bibi.storyapp.data.remote.retrofit.ApiService

class StoryRepository (private val apiService: ApiService) {
    fun getStory(token: String): LiveData<PagingData<ListStory>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token)
            }
        ).liveData
    }
}