package com.example.githubsearch.view

import android.app.Application
import com.example.githubsearch.model.repository.UsersRepository
import com.example.githubsearch.model.repository.room.users.UsersRoomDatabase

class UsersApplication : Application() {
    private val database by lazy { UsersRoomDatabase.getDatabase(this) }
    val repository by lazy { UsersRepository(database.getUsersDao()) }
}