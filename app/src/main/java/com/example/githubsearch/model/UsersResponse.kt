package com.example.githubsearch.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @SerializedName("items")
    val items: ArrayList<Users>?
)