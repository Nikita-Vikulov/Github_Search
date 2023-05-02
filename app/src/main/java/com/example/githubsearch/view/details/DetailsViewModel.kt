package com.example.githubsearch.view.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.model.Repository
import com.example.githubsearch.model.Users
import com.example.githubsearch.repository.ReposRepository
import com.example.githubsearch.repository.UsersRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val userRepository: UsersRepository,
    private val repoRepository: ReposRepository
) : ViewModel() {
    val myRepos: MutableLiveData<List<Repository>> = MutableLiveData()

    fun getReposByLogin(login: String) {
        viewModelScope.launch {
            myRepos.value = repoRepository.getReposByLogin(login)
        }
    }

    fun insert(user: Users) = viewModelScope.launch {
        userRepository.insertUser(user)
    }
}