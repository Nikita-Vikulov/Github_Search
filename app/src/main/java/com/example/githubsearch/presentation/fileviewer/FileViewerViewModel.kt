package com.example.githubsearch.presentation.fileviewer

import androidx.lifecycle.ViewModel
import com.example.githubsearch.R
import com.example.githubsearch.data.repository.ReposRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.Syntax
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FileViewerViewModel @Inject constructor(
    private val repoRepository: ReposRepository
) : ContainerHost<State, SideEffect>, ViewModel() {

    override val container = container<State, SideEffect>(State())

    fun processIntent(intent: Intent) = when (intent) {
        is LoadFile -> loadFile(intent.downloadUrl)
    }

    private inline fun intentCatching(
        crossinline block: suspend Syntax<State, SideEffect>.() -> Unit
    ) = intent {
        try {
            block()
        } catch (_: Exception) {
            reduce { state.copy(isLoading = false, errorMessageResId = R.string.failed_load_file) }
            postSideEffect(ShowToastRes(R.string.failed_load_file))
        }
    }

    private fun loadFile(downloadUrl: String) = intentCatching {
        reduce { state.copy(isLoading = true, errorMessage = null, errorMessageResId = null) }
        val text = repoRepository.getFileContent(downloadUrl)
        reduce { state.copy(isLoading = false, content = text) }
    }
}
