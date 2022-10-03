package com.fturkan.guser.ui.home.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fturkan.guser.api.RetrofitClient
import com.fturkan.guser.data.model.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel: ViewModel() {

    val user = MutableLiveData<UserDetailResponse>()

    fun setUserDetail(username: String) {
        fetchUserData(username)
    }

    fun fetchUserData(username: String){
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    Log.d("Failure", "${t.message}")
                }

            })
    }

    fun getUserDetail(): LiveData<UserDetailResponse> {
        return user
    }

}