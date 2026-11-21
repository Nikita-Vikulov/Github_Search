package com.example.githubsearch.presentation

import com.example.githubsearch.MainDispatcherRule
import com.example.githubsearch.data.repository.UsersRepository
import com.example.githubsearch.domain.model.Users
import com.example.githubsearch.presentation.common.UiState
import com.example.githubsearch.presentation.users.UsersViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

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
    fun initialState_isIdle() {
        assertEquals(UiState.Idle, viewModel.uiState.value)
    }

    @Test
    fun getUsersByLogin_success_emitsSuccessState() = runTest {
        val query = "octocat"
        val mockUsers = listOf(Users(login = "octocat", id = 1, avatarUrl = "", reposUrl = ""))
        whenever(userRepository.getUsersByLogin(query)).thenReturn(mockUsers)

        viewModel.getUsersByLogin(query)

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
        assertEquals(mockUsers, (state as UiState.Success).data)
    }

    @Test
    fun getUsersByLogin_error_emitsErrorState() = runTest {
        val query = "octocat"
        whenever(userRepository.getUsersByLogin(query)).thenThrow(RuntimeException("Network Error"))

        viewModel.getUsersByLogin(query)

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Error)
        assertEquals("Network Error", (state as UiState.Error).message)
    }
}
