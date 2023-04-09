package com.example.githubsearch.model.repository


import com.example.githubsearch.model.Users
import com.example.githubsearch.model.UsersResponse
import com.example.githubsearch.model.repository.api.RetrofitInstance
import com.example.githubsearch.model.repository.room.UsersDao
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UsersRepository(private val usersDao: UsersDao) {

    val allUsers: Flow<List<Users>> = usersDao.getAllUsers()

    suspend fun insertUser(users: Users) {
        usersDao.insert(users)
    }

    suspend fun getUsers(queryUser: String): Response<UsersResponse> {
        return RetrofitInstance.api.getSearchUsers(queryUser)
    }
}