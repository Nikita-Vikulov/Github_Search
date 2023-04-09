package com.example.githubsearch.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @Expose
    @SerializedName("total_count")
    val totalCount: Int, //переименовать, название поля вынести отдельно

    @Expose
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @Expose
    @SerializedName("items")
    val items: ArrayList<Users>?
)