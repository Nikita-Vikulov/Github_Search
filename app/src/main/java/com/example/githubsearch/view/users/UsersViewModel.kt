package com.example.githubsearch.view.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.model.Users
import com.example.githubsearch.model.UsersResponse
import com.example.githubsearch.model.repository.RetrofitRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class UsersViewModel: ViewModel() {
    private val repository = RetrofitRepository()
    val myUsers: MutableLiveData<Response<UsersResponse>> = MutableLiveData()

    fun getUsers() {
        viewModelScope.launch {
            myUsers.value = repository.getUsers()
        }

    }
}