package com.example.githubsearch.presentation.fileviewer

object FileViewerContract {
    data class State(
        val isLoading: Boolean = false,
        val content: String = "",
        val errorMessage: String? = null
    )

    sealed interface SideEffect {
        data class ShowToast(val message: String) : SideEffect
    }
}
