package com.fturkan.guser.ui.home.favorites

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fturkan.guser.data.local.UserDatabase
import com.fturkan.guser.data.model.User
import com.fturkan.guser.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : BaseViewModel(application) {

    private val favoriteUserList = MutableLiveData<ArrayList<User>>()

    private val database: UserDatabase? = UserDatabase.getDatabase(application)

    fun fetchFavoriteUsers() {
        CoroutineScope(Dispatchers.Default).launch {
            val list = database?.favoriteUserDao()?.getFavoriteUser()
            val userList = arrayListOf<User>()

            if (list != null) {
                for (favoriteUser in list) {
                    userList.add(User(favoriteUser.login!!, favoriteUser.uuid, favoriteUser.avatar_url!!))
                }
            }

            favoriteUserList.postValue(userList)
        }
    }

    fun getFavoriteUsersList(): MutableLiveData<ArrayList<User>> {
        return favoriteUserList
    }

}