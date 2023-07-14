package com.bibi.storyapp.data.response

import com.bibi.storyapp.data.model.Story
import com.google.gson.annotations.SerializedName

data class StoriesResponse(
    @SerializedName("listStory")
    val listStory: List<Story?>? = null,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
)