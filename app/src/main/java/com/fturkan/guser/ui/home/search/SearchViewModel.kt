package com.fturkan.guser.ui.home.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fturkan.guser.api.RetrofitClient
import com.fturkan.guser.data.model.User
import com.fturkan.guser.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {

    val userList = MutableLiveData<ArrayList<User>>()
    val progressBar = MutableLiveData<Boolean>()

    fun fetchSearchUsers(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        userList.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", "${t.message}")
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return userList
    }

}