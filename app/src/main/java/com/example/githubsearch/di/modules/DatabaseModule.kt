package com.example.githubsearch.di.modules

import android.content.Context
import androidx.room.Room
import com.example.githubsearch.model.repository.room.GithubDatabase
import com.example.githubsearch.model.repository.room.ReposDao
import com.example.githubsearch.model.repository.room.UsersDao
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): GithubDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GithubDatabase::class.java,
            "db"
        ).build()
    }

    @Provides
    fun provideUsersDao(database: GithubDatabase): UsersDao {
        return database.getUsersDao()
    }

    @Provides
    fun provideReposDao(database: GithubDatabase): ReposDao {
        return database.getReposDao()
    }
}
