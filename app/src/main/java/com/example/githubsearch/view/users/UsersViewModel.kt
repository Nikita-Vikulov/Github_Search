package com.example.githubsearch.view.users

import androidx.lifecycle.*
import com.example.githubsearch.model.Users
import com.example.githubsearch.model.UsersResponse
import com.example.githubsearch.model.repository.UsersRepository
import kotlinx.coroutines.launch
import retrofit2.Response


class UsersViewModel(val repository: UsersRepository) : ViewModel() {
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

/*
class UsersViewModel(application: Application) : AndroidViewModel(application) { // сделать в конструкторе репозиторий и инстанс БД

    private val repository = RetrofitRepository()
    val myUsers: MutableLiveData<Response<UsersResponse>> = MutableLiveData()
    private val context = application

    fun getUsers(queryUser: String) {
        viewModelScope.launch {
            myUsers.value = repository.getUsers(queryUser)
        }
    }

    fun initDatabase(){ // на уровне application (пункт 8)
        val daoUsers = UsersRoomDatabase.getInstance(context).getUsersDao()
        REALIZATION = UsersRepositoryRealization(daoUsers)
    }

    fun getAllUsers(): LiveData<List<Users>> {
        return REALIZATION.allUsers
    }
}*/
