package com.example.githubsearch.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.data.repository.UsersRepository
import com.example.githubsearch.domain.Users
import com.example.githubsearch.presentation.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val userRepository: UsersRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Users>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Users>>> = _uiState.asStateFlow()

    fun getUsersByLogin(queryUser: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val users = userRepository.getUsersByLogin(queryUser)
                _uiState.value = UiState.Success(users)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
