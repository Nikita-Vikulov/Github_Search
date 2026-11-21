package com.example.githubsearch.presentation.fileviewer

import androidx.lifecycle.ViewModel
import com.example.githubsearch.data.repository.ReposRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FileViewerViewModel @Inject constructor(
    private val repoRepository: ReposRepository
) : ContainerHost<FileViewerContract.State, FileViewerContract.SideEffect>, ViewModel() {

    override val container = container<FileViewerContract.State, FileViewerContract.SideEffect>(FileViewerContract.State())

    fun loadFile(downloadUrl: String) = intent {
        reduce { state.copy(isLoading = true, errorMessage = null) }
        try {
            val text = repoRepository.getFileContent(downloadUrl)
            reduce { state.copy(isLoading = false, content = text) }
        } catch (e: Exception) {
            val errorMsg = e.localizedMessage.orEmpty().ifBlank { "Failed to load file" }
            reduce { state.copy(isLoading = false, errorMessage = errorMsg) }
            postSideEffect(FileViewerContract.SideEffect.ShowToast(errorMsg))
        }
    }
}
