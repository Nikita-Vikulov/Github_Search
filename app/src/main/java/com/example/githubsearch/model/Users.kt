package com.example.githubsearch.model

import java.io.Serializable

data class Users(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val repos_url: String,
) : Serializable
