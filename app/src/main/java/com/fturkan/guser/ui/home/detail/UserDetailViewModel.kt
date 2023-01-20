package com.fturkan.guser.ui.home.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fturkan.guser.api.RetrofitClient
import com.fturkan.guser.data.local.FavoriteUser
import com.fturkan.guser.data.local.FavoriteUserDao
import com.fturkan.guser.data.local.UserDatabase
import com.fturkan.guser.data.model.UserDetailResponse
import com.fturkan.guser.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : BaseViewModel(application) {
    val user = MutableLiveData<UserDetailResponse>()

    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        fetchUserData(username)
    }

    fun fetchUserData(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.apiInstance
                .getUserDetail(username)
                .enqueue(object : Callback<UserDetailResponse> {
                    override fun onResponse(
                        call: Call<UserDetailResponse>,
                        response: Response<UserDetailResponse>
                    ) {
                        if (response.isSuccessful) {
                            user.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                        Log.d("Failure", "${t.message}")
                    }

                })
        }
    }

    fun getUserDetail(): LiveData<UserDetailResponse> {
        return user
    }

    fun addToFavorite(username: String, id: Int, avatar: String) {
        CoroutineScope(Dispatchers.Default).launch {
            val user = FavoriteUser(id, username, avatar)
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            userDao?.removeFromFavorite(id)
        }
    }

}