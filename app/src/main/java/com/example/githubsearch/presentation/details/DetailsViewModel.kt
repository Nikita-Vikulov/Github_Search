package com.example.githubsearch.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.data.repository.ReposRepository
import com.example.githubsearch.data.repository.UsersRepository
import com.example.githubsearch.domain.model.Repository
import com.example.githubsearch.domain.model.Users
import com.example.githubsearch.presentation.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val userRepository: UsersRepository,
    private val repoRepository: ReposRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Repository>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Repository>>> = _uiState.asStateFlow()

    fun getReposByLogin(login: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val repos = repoRepository.getReposByLogin(login)
                _uiState.value = UiState.Success(repos)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun insert(user: Users) = viewModelScope.launch {
        userRepository.insertUser(user)
    }
}
