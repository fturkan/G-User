package com.fturkan.guser.api

import com.fturkan.guser.data.model.User
import com.fturkan.guser.data.model.UserDetailResponse
import com.fturkan.guser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<User>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<User>

}