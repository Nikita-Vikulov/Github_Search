package com.example.githubsearch.presentation

import com.example.githubsearch.MainDispatcherRule
import com.example.githubsearch.R
import com.example.githubsearch.data.repository.UsersRepository
import com.example.githubsearch.domain.model.Users
import com.example.githubsearch.presentation.users.SearchUsers
import com.example.githubsearch.presentation.users.ShowToastRes
import com.example.githubsearch.presentation.users.UsersViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.orbitmvi.orbit.test.test

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val userRepository: UsersRepository = mock()
    private lateinit var viewModel: UsersViewModel

    @Before
    fun setUp() {
        viewModel = UsersViewModel(userRepository)
    }

    @Test
    fun getUsersByLogin_success_emitsLoadingAndUsersState() = runTest {
        val query = "octocat"
        val mockUsers = listOf(Users(login = "octocat", id = 1, avatarUrl = "", reposUrl = ""))
        whenever(userRepository.getUsersByLogin(query)).thenReturn(mockUsers)

        viewModel.test(this) {
            expectInitialState()
            containerHost.processIntent(SearchUsers(query))
            expectState { copy(isLoading = true, errorMessage = null, errorMessageResId = null) }
            expectState { copy(isLoading = false, users = mockUsers) }
        }
    }

    @Test
    fun getUsersByLogin_empty_postsToastSideEffect() = runTest {
        val query = "nonexistent"
        whenever(userRepository.getUsersByLogin(query)).thenReturn(emptyList())

        viewModel.test(this) {
            expectInitialState()
            containerHost.processIntent(SearchUsers(query))
            expectState { copy(isLoading = true, errorMessage = null, errorMessageResId = null) }
            expectState { copy(isLoading = false, users = emptyList()) }
            expectSideEffect(ShowToastRes(R.string.no_users_found))
        }
    }

    @Test
    fun getUsersByLogin_error_emitsErrorStateAndToast() = runTest {
        val query = "octocat"
        whenever(userRepository.getUsersByLogin(query)).thenThrow(RuntimeException("Network Error"))

        viewModel.test(this) {
            expectInitialState()
            containerHost.processIntent(SearchUsers(query))
            expectState { copy(isLoading = true, errorMessage = null, errorMessageResId = null) }
            expectState { copy(isLoading = false, errorMessageResId = R.string.unknown_error) }
            expectSideEffect(ShowToastRes(R.string.unknown_error))
        }
    }
}
