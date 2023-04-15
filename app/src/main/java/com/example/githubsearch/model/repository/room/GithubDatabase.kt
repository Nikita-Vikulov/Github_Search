package com.example.githubsearch.model.repository.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubsearch.model.Repository
import com.example.githubsearch.model.Users

@Database(entities = [Users::class, Repository::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun getUsersDao(): UsersDao
    abstract fun getReposDao(): ReposDao

    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        fun getDatabase(context: Context): GithubDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubDatabase::class.java,
                    "db"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}