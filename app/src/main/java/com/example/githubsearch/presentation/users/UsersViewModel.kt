package com.example.githubsearch.presentation.users

import androidx.lifecycle.ViewModel
import com.example.githubsearch.R
import com.example.githubsearch.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UsersRepository
) : ContainerHost<UsersContract.State, UsersContract.SideEffect>, ViewModel() {

    override val container = container<UsersContract.State, UsersContract.SideEffect>(UsersContract.State())

    fun getUsersByLogin(queryUser: String) = intent {
        reduce { state.copy(isLoading = true, errorMessage = null) }
        try {
            val users = userRepository.getUsersByLogin(queryUser)
            reduce { state.copy(isLoading = false, users = users) }
            if (users.isEmpty()) {
                postSideEffect(UsersContract.SideEffect.ShowToastRes(R.string.no_users_found))
            }
        } catch (e: Exception) {
            val errorMsg = e.localizedMessage.orEmpty().ifBlank { "Unknown error" }
            reduce { state.copy(isLoading = false, errorMessage = errorMsg) }
            postSideEffect(UsersContract.SideEffect.ShowToast(errorMsg))
        }
    }
}
