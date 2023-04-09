package com.example.githubsearch.view

import android.app.Application
import com.example.githubsearch.model.repository.ReposRepository
import com.example.githubsearch.model.repository.UsersRepository
import com.example.githubsearch.model.repository.room.GithubDatabase

class Application : Application() {
    private val database by lazy { GithubDatabase.getDatabase(this) }
    val userRepository by lazy { UsersRepository(database.getUsersDao()) }
    val repoRepository by lazy { ReposRepository(database.getReposDao()) }
}