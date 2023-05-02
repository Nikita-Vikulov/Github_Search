package com.example.githubsearch.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubsearch.model.Repository
import com.example.githubsearch.model.Users

@Database(entities = [Users::class, Repository::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun getUsersDao(): UsersDao
    abstract fun getReposDao(): ReposDao
}