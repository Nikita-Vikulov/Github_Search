package com.example.githubsearch.presentation.repository

import com.example.githubsearch.domain.model.RepoContentItem

object RepositoryContract {
    data class State(
        val isLoading: Boolean = false,
        val contents: List<RepoContentItem> = emptyList(),
        val currentPath: String = "",
        val pathStack: List<String> = emptyList(),
        val directoryCache: Map<String, List<RepoContentItem>> = emptyMap(),
        val errorMessage: String? = null
    )

    sealed interface SideEffect {
        data class ShowToast(val message: String) : SideEffect
        data class ShowToastRes(val messageResId: Int) : SideEffect
    }
}
