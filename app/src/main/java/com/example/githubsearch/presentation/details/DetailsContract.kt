package com.example.githubsearch.presentation.details

import com.example.githubsearch.domain.model.Repository

object DetailsContract {
    data class State(
        val isLoading: Boolean = false,
        val repos: List<Repository> = emptyList(),
        val errorMessage: String? = null
    )

    sealed interface SideEffect {
        data class ShowToast(val message: String) : SideEffect
        data class ShowToastRes(val messageResId: Int) : SideEffect
    }
}
