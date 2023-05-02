package com.example.githubsearch.di


import android.app.Application
import com.example.githubsearch.App
import com.example.githubsearch.di.modules.ApiModule
import com.example.githubsearch.di.modules.AppModule
import com.example.githubsearch.di.modules.DatabaseModule
import com.example.githubsearch.di.modules.ViewModelsModule
import com.example.githubsearch.repository.ReposRepository
import com.example.githubsearch.repository.UsersRepository
import com.example.githubsearch.view.MainActivity
import com.example.githubsearch.view.details.DetailsFragment
import com.example.githubsearch.view.history.HistoryFragment
import com.example.githubsearch.view.users.UsersFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        ViewModelsModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: Application): Builder
        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }

    override fun inject(application: App)
    fun inject(activity: MainActivity)
    fun inject(fragment: UsersFragment)
    fun inject(fragment: DetailsFragment)
    fun inject(fragment: HistoryFragment)

    fun userRepository(): UsersRepository
    fun repoRepository(): ReposRepository
}