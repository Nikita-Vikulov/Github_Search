package com.example.githubsearch

import android.app.Application
import com.example.githubsearch.di.AppComponent
import com.example.githubsearch.di.DaggerAppComponent
import com.example.githubsearch.di.modules.AppModule
import com.example.githubsearch.repository.ReposRepository
import com.example.githubsearch.repository.RepositoryMapper
import com.example.githubsearch.repository.UsersRepository
import com.example.githubsearch.room.GithubDatabase
import javax.inject.Inject


class App : Application() {

    companion object {
        lateinit var instance: App
    }

    @Inject
    lateinit var database: GithubDatabase

    @Inject
    lateinit var userRepository: UsersRepository

    @Inject
    lateinit var repoRepository: ReposRepository

    @Inject
    lateinit var repositoryMapper: RepositoryMapper

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .app(this)
            .appModule(AppModule(this))
            .build()

        appComponent.inject(this)
    }
}
