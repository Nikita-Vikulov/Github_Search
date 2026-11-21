package com.example.githubsearch.di.modules

import android.content.Context
import com.example.githubsearch.App
import com.example.githubsearch.data.AndroidNetworkStatus
import com.example.githubsearch.data.ApiService
import com.example.githubsearch.data.ReposRepository
import com.example.githubsearch.data.RepositoryMapper
import com.example.githubsearch.data.UsersRepository
import com.example.githubsearch.data.GithubDatabase
import com.example.githubsearch.domain.INetworkStatus
import dagger.Module
import dagger.Provides


@Module
class AppModule(private val application: App) {

    @Provides
    fun provideContext(): Context = application

    @Provides
    fun provideUsersRepository(
        database: GithubDatabase,
        networkStatus: INetworkStatus,
        apiService: ApiService
    ): UsersRepository {
        return UsersRepository(database.getUsersDao(), networkStatus, apiService)
    }

    @Provides
    fun provideReposRepository(
        database: GithubDatabase,
        networkStatus: INetworkStatus,
        apiService: ApiService,
        repositoryMapper: RepositoryMapper
    ): ReposRepository {
        return ReposRepository(database.getReposDao(), networkStatus, apiService, repositoryMapper)
    }

    @Provides
    fun provideRepositoryMapper(): RepositoryMapper {
        return RepositoryMapper()
    }

    @Provides
    fun provideNetworkStatus(): INetworkStatus {
        return AndroidNetworkStatus(application)
    }

}