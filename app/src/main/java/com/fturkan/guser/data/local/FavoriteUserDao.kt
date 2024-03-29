package com.fturkan.guser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {

    @Insert
    fun addToFavorite(user: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): List<FavoriteUser>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.uuid = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.uuid = :id")
    fun removeFromFavorite(id: Int): Int

}