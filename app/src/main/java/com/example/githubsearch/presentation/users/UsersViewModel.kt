package com.example.githubsearch.presentation.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.data.UsersRepository
import com.example.githubsearch.domain.Users
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val userRepository: UsersRepository
) : ViewModel() {
    private val _myUsers = MutableLiveData<List<Users>>()
    val myUsers: LiveData<List<Users>> = _myUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUsersByLogin(queryUser: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val users = userRepository.getUsersByLogin(queryUser)
                _myUsers.postValue(users)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
