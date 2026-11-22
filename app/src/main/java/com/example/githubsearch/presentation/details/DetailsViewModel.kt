package com.example.githubsearch.presentation.details

import androidx.lifecycle.ViewModel
import com.example.githubsearch.R
import com.example.githubsearch.data.repository.ReposRepository
import com.example.githubsearch.data.repository.UsersRepository
import com.example.githubsearch.domain.model.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.Syntax
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val userRepository: UsersRepository,
    private val repoRepository: ReposRepository
) : ContainerHost<State, SideEffect>, ViewModel() {

    override val container = container<State, SideEffect>(State())

    fun processIntent(intent: Intent) = when (intent) {
        is LoadUserRepos -> loadUserRepos(intent.user)
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

    private fun loadUserRepos(user: Users) = intentCatching {
        userRepository.insertUser(user)
        reduce { state.copy(isLoading = true, errorMessage = null, errorMessageResId = null) }
        val repos = repoRepository.getReposByLogin(user.login)
        reduce { state.copy(isLoading = false, repos = repos) }
        if (repos.isEmpty()) {
            postSideEffect(ShowToastRes(R.string.no_repos_found))
        }
    }
}
