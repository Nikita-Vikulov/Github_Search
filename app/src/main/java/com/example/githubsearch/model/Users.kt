package com.example.githubsearch.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users_table")
@Parcelize
data class Users(
    @SerializedName("login")
    val login: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("repos_url")
    val reposUrl: String

) : Parcelable
