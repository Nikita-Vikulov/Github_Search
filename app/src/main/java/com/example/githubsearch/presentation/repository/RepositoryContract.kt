package com.example.githubsearch.presentation.repository

import com.example.githubsearch.domain.model.RepoContentItem
import com.example.githubsearch.domain.model.Repository

data class State(
    val isLoading: Boolean = false,
    val contents: List<RepoContentItem> = emptyList(),
    val currentPath: String = "",
    val pathStack: List<String> = emptyList(),
    val directoryCache: Map<String, List<RepoContentItem>> = emptyMap(),
    val errorMessage: String? = null,
    val errorMessageResId: Int? = null
)

sealed interface Intent
data class LoadInitial(val repository: Repository) : Intent
data class ItemClicked(val repository: Repository, val item: RepoContentItem) : Intent
data object BackClicked : Intent

sealed interface SideEffect
data class NavigateToFile(val fileItem: RepoContentItem) : SideEffect
data object NavigateBack : SideEffect
data class ShowToast(val message: String) : SideEffect
data class ShowToastRes(val messageResId: Int) : SideEffect
