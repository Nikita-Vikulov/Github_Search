package com.example.githubsearch.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.data.ReposRepository
import com.example.githubsearch.data.UsersRepository
import com.example.githubsearch.domain.Repository
import com.example.githubsearch.domain.Users
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val userRepository: UsersRepository,
    private val repoRepository: ReposRepository
) : ViewModel() {
    val myRepos: MutableLiveData<List<Repository>> = MutableLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getReposByLogin(login: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                myRepos.value = repoRepository.getReposByLogin(login)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun insert(user: Users) = viewModelScope.launch {
        userRepository.insertUser(user)
    }
}
