package com.example.githubsearch.di

import android.content.Context
import com.example.githubsearch.App
import com.example.githubsearch.model.repository.ReposRepository
import com.example.githubsearch.model.repository.RepositoryMapper
import com.example.githubsearch.model.repository.UsersRepository
import com.example.githubsearch.model.repository.api.ApiService
import com.example.githubsearch.model.repository.room.GithubDatabase
import com.example.githubsearch.view.utils.AndroidNetworkStatus
import com.example.githubsearch.view.utils.INetworkStatus
import dagger.Module
import dagger.Provides


@Module
class AppModule(private val application: App) {

    @Provides
    fun provideContext(): Context = application

    @Provides
    fun provideGithubDatabase(): GithubDatabase {
        return GithubDatabase.getDatabase(application)
    }

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

   /* @Provides
    fun provideMainViewModel(
        userRepository: UsersRepository,
        repoRepository: ReposRepository
    ): MainViewModel {
        return MainViewModel(userRepository, repoRepository)
    }*/

    @Provides
    fun provideNetworkStatus(): INetworkStatus {
        return AndroidNetworkStatus(application)
    }

}


