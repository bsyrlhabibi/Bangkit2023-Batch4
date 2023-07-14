package com.bibi.storyapp.data.response

import com.bibi.storyapp.data.model.LoginResult
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("loginResult")
    val loginResult: LoginResult?,
)
