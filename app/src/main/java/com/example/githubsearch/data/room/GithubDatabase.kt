package com.example.githubsearch.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubsearch.domain.model.Repository
import com.example.githubsearch.domain.model.Users

@Database(entities = [Users::class, Repository::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun getUsersDao(): UsersDao
    abstract fun getReposDao(): ReposDao
}
