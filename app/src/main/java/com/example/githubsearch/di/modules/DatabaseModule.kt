package com.example.githubsearch.di.modules

import android.content.Context
import androidx.room.Room
import com.example.githubsearch.data.room.GithubDatabase
import com.example.githubsearch.data.room.ReposDao
import com.example.githubsearch.data.room.UsersDao
import com.example.githubsearch.util.DATA_BASE
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): GithubDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GithubDatabase::class.java,
            DATA_BASE
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
