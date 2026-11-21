package com.example.githubsearch.presentation.users

import com.example.githubsearch.domain.model.Users

object UsersContract {
    data class State(
        val isLoading: Boolean = false,
        val users: List<Users> = emptyList(),
        val errorMessage: String? = null
    )

    sealed interface SideEffect {
        data class ShowToast(val message: String) : SideEffect
        data class ShowToastRes(val messageResId: Int) : SideEffect
    }
}
