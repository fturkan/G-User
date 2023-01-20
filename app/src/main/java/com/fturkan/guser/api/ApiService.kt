package com.fturkan.guser.api

import com.fturkan.guser.data.model.User
import com.fturkan.guser.data.model.UserDetailResponse
import com.fturkan.guser.data.model.UserResponse
import com.fturkan.guser.util.Constants
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @JvmSuppressWildcards
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String,
        @Header("Authorization")
        authorization: String = Constants.AUTH_TOKEN
    ): Call<UserResponse>

    @JvmSuppressWildcards
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = Constants.AUTH_TOKEN
    ): Call<UserDetailResponse>

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = Constants.AUTH_TOKEN
    ): Call<User>

    @JvmSuppressWildcards
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = Constants.AUTH_TOKEN
    ): Call<User>

}