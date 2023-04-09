package com.example.githubsearch.view.users

import androidx.lifecycle.*
import com.example.githubsearch.model.Users
import com.example.githubsearch.model.UsersResponse
import com.example.githubsearch.model.repository.UsersRepository
import kotlinx.coroutines.launch
import retrofit2.Response


class UsersViewModel(private val repository: UsersRepository) : ViewModel() {
    val allUsers: LiveData<List<Users>> = repository.allUsers.asLiveData()
    val myUsers: MutableLiveData<Response<UsersResponse>> = MutableLiveData()
    fun getUsers(queryUser: String) {
        viewModelScope.launch {
            myUsers.value = repository.getUsers(queryUser)
        }
    }
    fun insert(users: Users) = viewModelScope.launch {
        repository.insertUser(users)
    }
}

class ViewModelFactory(private val repository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
