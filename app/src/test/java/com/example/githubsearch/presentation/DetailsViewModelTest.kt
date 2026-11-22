package com.example.githubsearch.presentation

import com.example.githubsearch.MainDispatcherRule
import com.example.githubsearch.R
import com.example.githubsearch.data.repository.ReposRepository
import com.example.githubsearch.data.repository.UsersRepository
import com.example.githubsearch.domain.model.Repository
import com.example.githubsearch.domain.model.Users
import com.example.githubsearch.presentation.details.DetailsViewModel
import com.example.githubsearch.presentation.details.LoadUserRepos
import com.example.githubsearch.presentation.details.ShowToastRes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.orbitmvi.orbit.test.test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val userRepository: UsersRepository = mock()
    private val repoRepository: ReposRepository = mock()
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setUp() {
        viewModel = DetailsViewModel(userRepository, repoRepository)
    }

    @Test
    fun getReposByLogin_success_emitsReposState() = runTest {
        val login = "octocat"
        val user = Users(login = login, id = 1)
        val mockRepos = listOf(Repository(id = 1, name = "Repo 1", login = login))
        whenever(repoRepository.getReposByLogin(login)).thenReturn(mockRepos)

        viewModel.test(this) {
            expectInitialState()
            containerHost.processIntent(LoadUserRepos(user))
            expectState { copy(isLoading = true, errorMessage = null, errorMessageResId = null) }
            expectState { copy(isLoading = false, repos = mockRepos) }
        }
    }

    @Test
    fun getReposByLogin_empty_postsToastSideEffect() = runTest {
        val login = "octocat"
        val user = Users(login = login, id = 1)
        whenever(repoRepository.getReposByLogin(login)).thenReturn(emptyList())

        viewModel.test(this) {
            expectInitialState()
            containerHost.processIntent(LoadUserRepos(user))
            expectState { copy(isLoading = true, errorMessage = null, errorMessageResId = null) }
            expectState { copy(isLoading = false, repos = emptyList()) }
            expectSideEffect(ShowToastRes(R.string.no_repos_found))
        }
    }

    @Test
    fun getReposByLogin_error_emitsErrorStateAndToast() = runTest {
        val login = "octocat"
        val user = Users(login = login, id = 1)
        whenever(repoRepository.getReposByLogin(login)).thenThrow(RuntimeException("Network Error"))

        viewModel.test(this) {
            expectInitialState()
            containerHost.processIntent(LoadUserRepos(user))
            expectState { copy(isLoading = true, errorMessage = null, errorMessageResId = null) }
            expectState { copy(isLoading = false, errorMessageResId = R.string.unknown_error) }
            expectSideEffect(ShowToastRes(R.string.unknown_error))
        }
    }

    @Test
    fun loadUserRepos_insertsUserAndEmitsReposState() = runTest {
        val login = "octocat"
        val user = Users(login = login, id = 1, avatarUrl = "", reposUrl = "")
        val mockRepos = listOf(Repository(id = 1, name = "Repo 1", login = login))
        whenever(repoRepository.getReposByLogin(login)).thenReturn(mockRepos)

        viewModel.test(this) {
            expectInitialState()
            containerHost.processIntent(LoadUserRepos(user))
            expectState { copy(isLoading = true, errorMessage = null, errorMessageResId = null) }
            expectState { copy(isLoading = false, repos = mockRepos) }
        }

        verify(userRepository).insertUser(user)
    }
}
