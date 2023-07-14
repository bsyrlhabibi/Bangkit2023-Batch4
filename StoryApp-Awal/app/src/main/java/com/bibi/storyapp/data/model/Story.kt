package com.bibi.storyapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("photoUrl")
    val photoUrl: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
) : Parcelable