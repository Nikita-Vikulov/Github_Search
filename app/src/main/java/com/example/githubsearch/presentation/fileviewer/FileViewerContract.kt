package com.example.githubsearch.presentation.fileviewer

data class State(
    val isLoading: Boolean = false,
    val content: String = "",
    val errorMessage: String? = null,
    val errorMessageResId: Int? = null
)

sealed interface Intent
data class LoadFile(val downloadUrl: String) : Intent

sealed interface SideEffect
data class ShowToast(val message: String) : SideEffect
data class ShowToastRes(val messageResId: Int) : SideEffect
