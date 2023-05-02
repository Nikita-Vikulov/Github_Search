package com.example.githubsearch.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubsearch.model.Users
import com.example.githubsearch.repository.UsersRepository
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val userRepository: UsersRepository
) : ViewModel() {
    val allUsers: LiveData<List<Users>> = userRepository.allUsers.asLiveData()
}
