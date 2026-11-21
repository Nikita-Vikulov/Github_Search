package com.example.githubsearch.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubsearch.data.repository.UsersRepository
import com.example.githubsearch.domain.model.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val userRepository: UsersRepository
) : ViewModel() {
    val allUsers: LiveData<List<Users>> = userRepository.allUsers.asLiveData()
}
