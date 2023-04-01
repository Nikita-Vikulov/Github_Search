package com.example.githubsearch.view.users

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.githubsearch.MAIN
import com.example.githubsearch.REALIZATION
import com.example.githubsearch.model.Users
import com.example.githubsearch.model.UsersResponse
import com.example.githubsearch.model.repository.RetrofitRepository
import com.example.githubsearch.model.repository.room.UsersRoomDatabase
import com.example.githubsearch.model.repository.room.repository.UsersRepositoryRealization
import kotlinx.coroutines.launch
import retrofit2.Response

class UsersViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RetrofitRepository()
    val myUsers: MutableLiveData<Response<UsersResponse>> = MutableLiveData()
    private val context = application

    fun getUsers(queryUser: String) {
        viewModelScope.launch {
            myUsers.value = repository.getUsers(queryUser)
        }
    }

    fun initDatabase(){
        try{
        val daoUsers = UsersRoomDatabase.getInstance(context).getUsersDao()
        REALIZATION = UsersRepositoryRealization(daoUsers)
        } catch (e: Exception) {
            Toast.makeText(MAIN, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllUsers(): LiveData<List<Users>> {
        return REALIZATION.allUsers
    }
}