package com.example.githubsearch.presentation.users

import com.example.githubsearch.domain.model.Users

data class State(
    val isLoading: Boolean = false,
    val users: List<Users> = emptyList(),
    val errorMessage: String? = null,
    val errorMessageResId: Int? = null
)

sealed interface Intent
data class SearchUsers(val query: String) : Intent

sealed interface SideEffect
data class ShowToast(val message: String) : SideEffect
data class ShowToastRes(val messageResId: Int) : SideEffect
