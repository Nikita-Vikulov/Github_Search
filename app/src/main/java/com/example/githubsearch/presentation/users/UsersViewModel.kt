package com.example.githubsearch.presentation.users

import androidx.lifecycle.ViewModel
import com.example.githubsearch.R
import com.example.githubsearch.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.Syntax
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UsersRepository
) : ContainerHost<State, SideEffect>, ViewModel() {

    override val container = container<State, SideEffect>(State()) {
        searchUsers("android")
    }

    fun processIntent(intent: Intent) = when (intent) {
        is SearchUsers -> searchUsers(intent.query)
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

    private fun searchUsers(queryUser: String) = intentCatching {
        val query = queryUser.ifBlank { "android" }
        reduce { state.copy(isLoading = true, errorMessage = null, errorMessageResId = null) }
        val users = userRepository.getUsersByLogin(query)
        reduce { state.copy(isLoading = false, users = users) }
        if (users.isEmpty()) {
            postSideEffect(ShowToastRes(R.string.no_users_found))
        }
    }
}
