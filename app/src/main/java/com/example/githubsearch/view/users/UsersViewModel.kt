package com.example.githubsearch.view.users

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.REALIZATION
import com.example.githubsearch.model.Users
import com.example.githubsearch.model.UsersResponse
import com.example.githubsearch.model.repository.RetrofitRepository
import com.example.githubsearch.model.repository.room.UsersRoomDatabase
import com.example.githubsearch.model.repository.room.repository.UsersRepositoryRealization
import kotlinx.coroutines.launch
import retrofit2.Response

class UsersViewModel(application: Application) : AndroidViewModel(application) { // сделать в конструкторе репозиторий и инстанс БД

    private val repository = RetrofitRepository()
    val myUsers: MutableLiveData<Response<UsersResponse>> = MutableLiveData()
    private val context = application

    fun getUsers(queryUser: String) {
        viewModelScope.launch {
            myUsers.value = repository.getUsers(queryUser)
        }
    }

    fun initDatabase(){
        val daoUsers = UsersRoomDatabase.getInstance(context).getUsersDao()
        REALIZATION = UsersRepositoryRealization(daoUsers)
    }

    fun getAllUsers(): LiveData<List<Users>> {
        return REALIZATION.allUsers
    }
}