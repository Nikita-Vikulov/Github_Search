package com.example.githubsearch.presentation

import com.example.githubsearch.MainDispatcherRule
import com.example.githubsearch.data.repository.ReposRepository
import com.example.githubsearch.data.repository.UsersRepository
import com.example.githubsearch.domain.model.Repository
import com.example.githubsearch.domain.model.Users
import com.example.githubsearch.presentation.common.UiState
import com.example.githubsearch.presentation.details.DetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

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
    fun getReposByLogin_success_emitsSuccessState() = runTest {
        val login = "octocat"
        val mockRepos = listOf(Repository(id = 1, name = "Repo 1", login = login))
        whenever(repoRepository.getReposByLogin(login)).thenReturn(mockRepos)

        viewModel.getReposByLogin(login)

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
        assertEquals(mockRepos, (state as UiState.Success).data)
    }

    @Test
    fun getReposByLogin_error_emitsErrorState() = runTest {
        val login = "octocat"
        whenever(repoRepository.getReposByLogin(login)).thenThrow(RuntimeException("Failed to fetch repos"))

        viewModel.getReposByLogin(login)

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Error)
        assertEquals("Failed to fetch repos", (state as UiState.Error).message)
    }

    @Test
    fun insertUser_callsUserRepository() = runTest {
        val user = Users(login = "octocat", id = 1, avatarUrl = "", reposUrl = "")

        viewModel.insert(user)

        verify(userRepository).insertUser(user)
    }
}
