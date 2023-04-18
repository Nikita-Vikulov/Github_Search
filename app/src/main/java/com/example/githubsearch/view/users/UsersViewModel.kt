package com.example.githubsearch.view.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.model.Users
import com.example.githubsearch.model.repository.UsersRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val userRepository: UsersRepository
) : ViewModel() {
    private val _myUsers = MutableLiveData<List<Users>>()
    val myUsers: LiveData<List<Users>> = _myUsers

    fun getUsersByLogin(queryUser: String) {
        viewModelScope.launch {
            val users = userRepository.getUsersByLogin(queryUser)
            _myUsers.postValue(users)
        }
    }
}