package com.example.githubsearch.presentation.repository

import androidx.lifecycle.ViewModel
import com.example.githubsearch.data.repository.ReposRepository
import com.example.githubsearch.domain.model.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val repoRepository: ReposRepository
) : ContainerHost<RepositoryContract.State, RepositoryContract.SideEffect>, ViewModel() {

    override val container = container<RepositoryContract.State, RepositoryContract.SideEffect>(RepositoryContract.State())

    fun loadContents(repository: Repository, path: String = "", pushToStack: Boolean = true) = intent {
        val cached = state.directoryCache[path]
        if (cached != null) {
            val newStack = if (pushToStack && path != state.currentPath) {
                state.pathStack + state.currentPath
            } else {
                state.pathStack
            }
            reduce {
                state.copy(
                    isLoading = false,
                    contents = cached,
                    currentPath = path,
                    pathStack = newStack,
                    errorMessage = null
                )
            }
            return@intent
        }

        reduce { state.copy(isLoading = true, errorMessage = null) }
        try {
            val owner = repository.login.ifBlank { repository.fullName?.substringBefore("/") ?: "" }
            val repoName = repository.name.orEmpty().ifBlank { repository.fullName?.substringAfter("/") ?: "" }
            if (owner.isBlank() || repoName.isBlank()) {
                reduce { state.copy(isLoading = false, errorMessage = "Invalid repository info") }
                return@intent
            }
            val contents = repoRepository.getRepoContents(owner, repoName, path)
            val newCache = state.directoryCache + (path to contents)
            val newStack = if (pushToStack && path != state.currentPath && state.currentPath.isNotBlank()) {
                state.pathStack + state.currentPath
            } else {
                state.pathStack
            }

            reduce {
                state.copy(
                    isLoading = false,
                    contents = contents,
                    currentPath = path,
                    pathStack = newStack,
                    directoryCache = newCache
                )
            }
        } catch (e: Exception) {
            val errorMsg = e.localizedMessage.orEmpty().ifBlank { "Unknown error" }
            reduce { state.copy(isLoading = false, errorMessage = errorMsg) }
            postSideEffect(RepositoryContract.SideEffect.ShowToast(errorMsg))
        }
    }

    fun onBackPressed(): Boolean {
        val currentState = container.stateFlow.value
        if (currentState.pathStack.isNotEmpty()) {
            intent {
                val previousPath = state.pathStack.last()
                val newStack = state.pathStack.dropLast(1)
                val cached = state.directoryCache[previousPath].orEmpty()
                reduce {
                    state.copy(
                        contents = cached,
                        currentPath = previousPath,
                        pathStack = newStack
                    )
                }
            }
            return true
        }
        return false
    }
}
