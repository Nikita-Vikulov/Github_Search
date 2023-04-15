package com.example.githubsearch.view.details

import androidx.lifecycle.*
import com.example.githubsearch.model.Repository
import com.example.githubsearch.model.Users
import com.example.githubsearch.model.repository.ReposRepository
import com.example.githubsearch.model.repository.UsersRepository
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val userRepository: UsersRepository,
    private val repoRepository: ReposRepository
) : ViewModel() {

    val myRepos: MutableLiveData<List<Repository>> = MutableLiveData()

    fun getRepos(login: String) {
        viewModelScope.launch {
            myRepos.value = repoRepository.getReposByLogin(login)
        }
    }

    fun insert(user: Users) = viewModelScope.launch {
        userRepository.insertUser(user)
    }
}

class ViewModelFactory(
    private val userRepository: UsersRepository,
    private val repoRepository: ReposRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(userRepository, repoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}