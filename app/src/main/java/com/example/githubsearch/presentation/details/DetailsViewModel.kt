package com.example.githubsearch.presentation.details

import androidx.lifecycle.ViewModel
import com.example.githubsearch.R
import com.example.githubsearch.data.repository.ReposRepository
import com.example.githubsearch.data.repository.UsersRepository
import com.example.githubsearch.domain.model.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val userRepository: UsersRepository,
    private val repoRepository: ReposRepository
) : ContainerHost<DetailsContract.State, DetailsContract.SideEffect>, ViewModel() {

    override val container = container<DetailsContract.State, DetailsContract.SideEffect>(DetailsContract.State())

    fun getReposByLogin(login: String) = intent {
        reduce { state.copy(isLoading = true, errorMessage = null) }
        try {
            val repos = repoRepository.getReposByLogin(login)
            reduce { state.copy(isLoading = false, repos = repos) }
            if (repos.isEmpty()) {
                postSideEffect(DetailsContract.SideEffect.ShowToastRes(R.string.no_repos_found))
            }
        } catch (e: Exception) {
            val errorMsg = e.localizedMessage.orEmpty().ifBlank { "Unknown error" }
            reduce { state.copy(isLoading = false, errorMessage = errorMsg) }
            postSideEffect(DetailsContract.SideEffect.ShowToast(errorMsg))
        }
    }

    fun insert(user: Users) = intent {
        userRepository.insertUser(user)
    }
}
