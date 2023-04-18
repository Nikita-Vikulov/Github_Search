package com.example.githubsearch

import androidx.lifecycle.*
import com.example.githubsearch.model.Repository
import com.example.githubsearch.model.Users
import com.example.githubsearch.model.repository.ReposRepository
import com.example.githubsearch.model.repository.UsersRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UsersRepository,
    private val repoRepository: ReposRepository
) : ViewModel() {
    val allUsers: LiveData<List<Users>> = userRepository.allUsers.asLiveData()
    private val _myUsers = MutableLiveData<List<Users>>()
    val myUsers: LiveData<List<Users>> = _myUsers
    val myRepos: MutableLiveData<List<Repository>> = MutableLiveData()

    fun getReposByLogin(login: String) {
        viewModelScope.launch {
            myRepos.value = repoRepository.getReposByLogin(login)
        }
    }

    fun getUsersByLogin(queryUser: String) {
        viewModelScope.launch {
            val users = userRepository.getUsersByLogin(queryUser)
            _myUsers.postValue(users)
        }
    }

    fun insert(user: Users) = viewModelScope.launch {
        userRepository.insertUser(user)
    }
}