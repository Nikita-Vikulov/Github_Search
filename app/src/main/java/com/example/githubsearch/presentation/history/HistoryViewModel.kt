package com.example.githubsearch.presentation.history

import androidx.lifecycle.ViewModel
import com.example.githubsearch.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val userRepository: UsersRepository
) : ContainerHost<HistoryContract.State, Nothing>, ViewModel() {

    override val container = container<HistoryContract.State, Nothing>(HistoryContract.State()) {
        userRepository.allUsers.collect { list ->
            intent {
                reduce { state.copy(users = list) }
            }
        }
    }
}
