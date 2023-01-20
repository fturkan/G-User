package com.fturkan.guser.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    @PrimaryKey(autoGenerate = true)
    var uuid: Int,
    @ColumnInfo(name = "login")
    @SerializedName("login")
    val login: String?,
    val avatar_url: String?
)