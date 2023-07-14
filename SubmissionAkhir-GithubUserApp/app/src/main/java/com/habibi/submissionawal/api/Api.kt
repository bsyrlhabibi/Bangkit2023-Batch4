package com.habibi.submissionawal.api

import com.habibi.submissionawal.model.Detail
import com.habibi.submissionawal.model.User
import com.habibi.submissionawal.model.UserResponds
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String,
        @Header("Authorization") token: String,
    ): Call<UserResponds>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String,
        @Header("Authorization") token: String,
    ): Call<Detail>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
        @Header("Authorization") token: String,
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
        @Header("Authorization") token: String,
    ): Call<ArrayList<User>>
}

