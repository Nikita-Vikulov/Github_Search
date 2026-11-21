package com.example.githubsearch.di.modules

import com.example.githubsearch.data.api.ApiService
import com.example.githubsearch.data.repository.ReposRepository
import com.example.githubsearch.data.repository.RepositoryMapper
import com.example.githubsearch.data.repository.UsersRepository
import com.example.githubsearch.data.room.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUsersRepository(
        database: GithubDatabase,
        apiService: ApiService
    ): UsersRepository {
        return UsersRepository(database.getUsersDao(), apiService)
    }

    @Provides
    @Singleton
    fun provideReposRepository(
        database: GithubDatabase,
        apiService: ApiService,
        repositoryMapper: RepositoryMapper
    ): ReposRepository {
        return ReposRepository(database.getReposDao(), apiService, repositoryMapper)
    }

    @Provides
    @Singleton
    fun provideRepositoryMapper(): RepositoryMapper {
        return RepositoryMapper()
    }
}
