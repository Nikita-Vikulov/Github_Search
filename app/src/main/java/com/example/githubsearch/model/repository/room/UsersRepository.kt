package com.example.githubsearch.model.repository.room

import android.support.annotation.WorkerThread
import com.example.githubsearch.model.Users
import com.example.githubsearch.model.UsersResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UsersRepository(private val usersDao: UsersDao) {

    val allUsers: Flow<List<Users>> = usersDao.getAllUsers()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertUser(users: Users) {
        usersDao.insert(users)
    }

    fun getUsers(queryUser: String): Response<UsersResponse>? {

    }
}