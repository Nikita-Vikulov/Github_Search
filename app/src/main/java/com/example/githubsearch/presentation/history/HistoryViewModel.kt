package com.example.githubsearch.presentation.history

import androidx.lifecycle.ViewModel
import com.example.githubsearch.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    userRepository: UsersRepository
) : ContainerHost<State, SideEffect>, ViewModel() {

    override val container = container<State, SideEffect>(State()) {
        userRepository.allUsers.collect { list ->
            intent {
                reduce { state.copy(users = list) }
            }
        }
    }

    fun processIntent(intent: Intent) = when (intent) {
        is UserClicked -> intent {
            postSideEffect(NavigateToDetails(intent.user))
        }
        is BackClicked -> intent {
            postSideEffect(NavigateBack)
        }
    }
}
