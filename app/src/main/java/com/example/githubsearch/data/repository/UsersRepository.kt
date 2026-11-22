package com.example.githubsearch.data.repository

import com.example.githubsearch.data.api.ApiService
import com.example.githubsearch.data.room.UsersDao
import com.example.githubsearch.domain.model.Users
import kotlinx.coroutines.flow.Flow

class UsersRepository(
    private val usersDao: UsersDao,
    private val apiService: ApiService
) {

    val allUsers: Flow<List<Users>> = usersDao.getAllUsers()

    suspend fun insertUser(users: Users) {
        usersDao.insert(users)
    }

    suspend fun getUsersByLogin(queryUser: String): List<Users> {
        val searchQuery = "%$queryUser%"
        return try {
            val response = apiService.getSearchUsers(queryUser)
            if (response.isSuccessful) {
                response.body()?.items.orEmpty()
            } else {
                usersDao.getUsersByLogin(searchQuery)
            }
        } catch (_: Exception) {
            usersDao.getUsersByLogin(searchQuery)
        }
    }
}
