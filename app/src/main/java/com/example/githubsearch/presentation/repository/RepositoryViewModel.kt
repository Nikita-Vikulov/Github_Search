package com.example.githubsearch.presentation.repository

import androidx.lifecycle.ViewModel
import com.example.githubsearch.R
import com.example.githubsearch.data.repository.ReposRepository
import com.example.githubsearch.domain.model.RepoItemType
import com.example.githubsearch.domain.model.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.Syntax
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val repoRepository: ReposRepository
) : ContainerHost<State, SideEffect>, ViewModel() {

    override val container = container<State, SideEffect>(State())

    fun processIntent(intent: Intent) = when (intent) {
        is LoadInitial -> intent {
            if (state.contents.isEmpty() && !state.isLoading && state.currentPath.isBlank()) {
                loadContents(intent.repository, "", pushToStack = false)
            }
        }
        is ItemClicked -> intent {
            when (intent.item.type) {
                RepoItemType.DIRECTORY -> loadContents(intent.repository, intent.item.path, pushToStack = true)
                RepoItemType.FILE -> if (!intent.item.downloadUrl.isNullOrBlank()) {
                    postSideEffect(NavigateToFile(intent.item))
                }
            }
        }
        is BackClicked -> intent {
            if (state.pathStack.isNotEmpty()) {
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
            } else {
                postSideEffect(NavigateBack)
            }
        }
    }

    private inline fun intentCatching(
        crossinline block: suspend Syntax<State, SideEffect>.() -> Unit
    ) = intent {
        try {
            block()
        } catch (_: Exception) {
            reduce { state.copy(isLoading = false, errorMessageResId = R.string.unknown_error) }
            postSideEffect(ShowToastRes(R.string.unknown_error))
        }
    }

    private fun loadContents(repository: Repository, path: String, pushToStack: Boolean) = intentCatching {
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
                    errorMessage = null,
                    errorMessageResId = null
                )
            }
            return@intentCatching
        }

        reduce { state.copy(isLoading = true, errorMessage = null, errorMessageResId = null) }

        val owner = repository.login.ifBlank { repository.fullName?.substringBefore("/").orEmpty() }
        val repoName = repository.name.orEmpty().ifBlank { repository.fullName?.substringAfter("/").orEmpty() }
        if (owner.isBlank() || repoName.isBlank()) {
            reduce { state.copy(isLoading = false, errorMessageResId = R.string.invalid_repo_info) }
            postSideEffect(ShowToastRes(R.string.invalid_repo_info))
            return@intentCatching
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
    }
}
