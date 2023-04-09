package com.example.githubsearch.view.details

import androidx.lifecycle.*
import com.example.githubsearch.model.ReposResponse
import com.example.githubsearch.model.repository.ReposRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailsViewModel(private val repository: ReposRepository) : ViewModel() {

    val myRepos: MutableLiveData<Response<List<ReposResponse>>> = MutableLiveData()
    fun getRepos(login: String) {
        viewModelScope.launch {
            myRepos.value = repository.getReposByLogin(login)
        }
    }
    fun insert(repos: ReposResponse) = viewModelScope.launch {
        repository.insertRepos(repos)
    }
}

class ViewModelFactory(private val repository: ReposRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}