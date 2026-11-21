package com.example.githubsearch.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubsearch.domain.Repository
import com.example.githubsearch.domain.Users

@Database(entities = [Users::class, Repository::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun getUsersDao(): UsersDao
    abstract fun getReposDao(): ReposDao
}
