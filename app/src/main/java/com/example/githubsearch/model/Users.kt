package com.example.githubsearch.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "users_table")
data class Users(
    @Expose
    @SerializedName("login")
    val login: String,

    @Expose
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @Expose
    @SerializedName("avatar_url")
    val avatarUrl: String,

    @Expose
    @SerializedName("repos_url")
    val reposUrl: String
) : Serializable
