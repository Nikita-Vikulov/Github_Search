package com.example.githubsearch.presentation.details

import com.example.githubsearch.domain.model.Repository
import com.example.githubsearch.domain.model.Users

data class State(
    val isLoading: Boolean = false,
    val repos: List<Repository> = emptyList(),
    val errorMessage: String? = null,
    val errorMessageResId: Int? = null
)

sealed interface Intent
data class LoadUserRepos(val user: Users) : Intent

sealed interface SideEffect
data class ShowToast(val message: String) : SideEffect
data class ShowToastRes(val messageResId: Int) : SideEffect
