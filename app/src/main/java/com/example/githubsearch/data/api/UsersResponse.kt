package com.example.githubsearch.data.api

import com.example.githubsearch.domain.model.Users
import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("total_count")
    val totalCount: Int = 0,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean = false,

    @SerializedName("items")
    val items: List<Users>? = null
)
