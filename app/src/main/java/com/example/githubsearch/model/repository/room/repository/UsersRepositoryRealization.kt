package com.example.githubsearch.model.repository.room.repository

import androidx.lifecycle.LiveData
import com.example.githubsearch.model.Users
import com.example.githubsearch.model.repository.room.UsersDao

class UsersRepositoryRealization(private val usersDao: UsersDao) : UsersRepository {
    override val allUsers: LiveData<List<Users>>
        get() = usersDao.getAllUsers()


    override suspend fun insertUser(users: Users, onSuccess: () -> Unit) {
        usersDao.insert(users)
        onSuccess()
    }

    override suspend fun deleteUser(users: Users, onSuccess: () -> Unit) {
        usersDao.delete(users)
        onSuccess()
    }

}