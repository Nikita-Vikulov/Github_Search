package com.example.githubsearch.di.modules

import android.content.Context
import androidx.room.Room
import com.example.githubsearch.data.room.GithubDatabase
import com.example.githubsearch.data.room.ReposDao
import com.example.githubsearch.data.room.UsersDao
import com.example.githubsearch.util.DATA_BASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GithubDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GithubDatabase::class.java,
            DATA_BASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideUsersDao(database: GithubDatabase): UsersDao {
        return database.getUsersDao()
    }

    @Provides
    @Singleton
    fun provideReposDao(database: GithubDatabase): ReposDao {
        return database.getReposDao()
    }
}
