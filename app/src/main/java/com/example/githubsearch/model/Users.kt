package com.example.githubsearch.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users_table")
data class Users(
    val login: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val avatar_url: String,
    val repos_url: String,
) : Serializable
