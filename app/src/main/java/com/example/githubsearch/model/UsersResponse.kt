package com.example.githubsearch.model

data class UsersResponse(
    val total_count: Int, //переименовать, название поля вынести отдельно
    val incomplete_results: Boolean,
    val items: ArrayList<Users>?
)