package com.example.githubsearch.model.repository.room.repository

import androidx.lifecycle.LiveData
import com.example.githubsearch.model.Users


interface UsersRepository {
    val allUsers: LiveData<List<Users>>
    suspend fun insertUser(users: Users, onSuccess: () -> Unit)
    suspend fun deleteUser(users: Users, onSuccess: () -> Unit)
}