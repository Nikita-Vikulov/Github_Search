package com.example.githubsearch.presentation.history

import com.example.githubsearch.domain.model.Users

data class State(
    val users: List<Users> = emptyList()
)

sealed interface Intent
data class UserClicked(val user: Users) : Intent
data object BackClicked : Intent

sealed interface SideEffect
data class NavigateToDetails(val user: Users) : SideEffect
data object NavigateBack : SideEffect
