package com.example.githubsearch.di.modules

import android.content.Context
import com.example.githubsearch.data.api.ApiService
import com.example.githubsearch.data.repository.ReposRepository
import com.example.githubsearch.data.repository.RepositoryMapper
import com.example.githubsearch.data.repository.UsersRepository
import com.example.githubsearch.data.room.GithubDatabase
import com.example.githubsearch.data.utils.AndroidNetworkStatus
import com.example.githubsearch.domain.interfaces.INetworkStatus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUsersRepository(
        database: GithubDatabase,
        networkStatus: INetworkStatus,
        apiService: ApiService
    ): UsersRepository {
        return UsersRepository(database.getUsersDao(), networkStatus, apiService)
    }

    @Provides
    @Singleton
    fun provideReposRepository(
        database: GithubDatabase,
        networkStatus: INetworkStatus,
        apiService: ApiService,
        repositoryMapper: RepositoryMapper
    ): ReposRepository {
        return ReposRepository(database.getReposDao(), networkStatus, apiService, repositoryMapper)
    }

    @Provides
    @Singleton
    fun provideRepositoryMapper(): RepositoryMapper {
        return RepositoryMapper()
    }

    @Provides
    @Singleton
    fun provideNetworkStatus(
        @ApplicationContext context: Context
    ): INetworkStatus {
        return AndroidNetworkStatus(context)
    }
}
