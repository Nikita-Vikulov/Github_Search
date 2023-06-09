package com.example.githubsearch.di.modules

import android.content.Context
import com.example.githubsearch.App
import com.example.githubsearch.api.ApiService
import com.example.githubsearch.repository.ReposRepository
import com.example.githubsearch.repository.RepositoryMapper
import com.example.githubsearch.repository.UsersRepository
import com.example.githubsearch.room.GithubDatabase
import com.example.githubsearch.view.utils.AndroidNetworkStatus
import com.example.githubsearch.view.utils.INetworkStatus
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


