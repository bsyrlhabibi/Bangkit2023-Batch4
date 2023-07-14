package com.habibi.submissionawal.dao

import com.habibi.submissionawal.model.Detail
import com.habibi.submissionawal.model.User
import com.habibi.submissionawal.model.UserResponds
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {
    @GET("search/users")
    @Headers("Authorization: Token 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")
    fun getSearchUsers(
        @Query("q") query: String,
    ): Call<UserResponds>

    @GET("users/{username}")
    @Headers("Authorization: Token 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")
    fun getDetail(
        @Path("username") username: String,
    ): Call<Detail>

    @GET("users/{username}/followers")
    @Headers("Authorization: Token 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")
    fun getFollowers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: Token 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")
    fun getFollowing(
        @Path("username") username: String,
    ): Call<ArrayList<User>>
}
