package com.example.githubsearch.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubsearch.domain.model.Repository
import com.example.githubsearch.domain.model.Users
import com.example.githubsearch.presentation.details.DetailsScreen
import com.example.githubsearch.presentation.details.DetailsViewModel
import com.example.githubsearch.presentation.fileviewer.FileViewerScreen
import com.example.githubsearch.presentation.history.HistoryScreen
import com.example.githubsearch.presentation.history.HistoryViewModel
import com.example.githubsearch.presentation.repository.RepositoryScreen
import com.example.githubsearch.presentation.users.UsersScreen
import com.example.githubsearch.presentation.users.UsersViewModel

@Composable
fun GithubNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "users"
    ) {
        composable("users") {
            val viewModel: UsersViewModel = hiltViewModel()
            UsersScreen(
                viewModel = viewModel,
                onUserClick = { user ->
                    val encodedAvatar = Uri.encode(user.avatarUrl ?: "")
                    val login = Uri.encode(user.login)
                    navController.navigate("details/$login/$encodedAvatar/${user.id}")
                },
                onHistoryClick = {
                    navController.navigate("history")
                }
            )
        }

        composable(
            route = "details/{login}/{avatarUrl}/{id}",
            arguments = listOf(
                navArgument("login") { type = NavType.StringType },
                navArgument("avatarUrl") { type = NavType.StringType },
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val login = Uri.decode(backStackEntry.arguments?.getString("login") ?: "")
            val avatarUrl = Uri.decode(backStackEntry.arguments?.getString("avatarUrl") ?: "")
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            val user = Users(login = login, id = id, avatarUrl = avatarUrl, reposUrl = "")
            val viewModel: DetailsViewModel = hiltViewModel()

            DetailsScreen(
                user = user,
                viewModel = viewModel,
                onRepoClick = { repo ->
                    val name = Uri.encode(repo.name ?: "Unknown")
                    val fullName = Uri.encode(repo.fullName ?: "")
                    val language = Uri.encode(repo.language ?: "")
                    val description = Uri.encode(repo.description ?: "")
                    navController.navigate("repo_detail/$name/$fullName/$language/${repo.forksCount}/$description")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("history") {
            val viewModel: HistoryViewModel = hiltViewModel()
            HistoryScreen(
                viewModel = viewModel,
                onUserClick = { user ->
                    val encodedAvatar = Uri.encode(user.avatarUrl ?: "")
                    val login = Uri.encode(user.login)
                    navController.navigate("details/$login/$encodedAvatar/${user.id}")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "repo_detail/{name}/{fullName}/{language}/{forksCount}/{description}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("fullName") { type = NavType.StringType },
                navArgument("language") { type = NavType.StringType },
                navArgument("forksCount") { type = NavType.IntType },
                navArgument("description") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = Uri.decode(backStackEntry.arguments?.getString("name") ?: "")
            val fullName = Uri.decode(backStackEntry.arguments?.getString("fullName") ?: "")
            val language = Uri.decode(backStackEntry.arguments?.getString("language") ?: "")
            val forksCount = backStackEntry.arguments?.getInt("forksContext") ?: 0
            val description = Uri.decode(backStackEntry.arguments?.getString("description") ?: "")

            val repository = Repository(
                name = name,
                fullName = fullName,
                language = language,
                forksCount = forksCount,
                description = description
            )

            RepositoryScreen(
                repository = repository,
                onFileClick = { fileItem ->
                    val fileName = Uri.encode(fileItem.name)
                    val downloadUrl = Uri.encode(fileItem.downloadUrl.orEmpty())
                    navController.navigate("file_viewer/$fileName/$downloadUrl")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "file_viewer/{fileName}/{downloadUrl}",
            arguments = listOf(
                navArgument("fileName") { type = NavType.StringType },
                navArgument("downloadUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val fileName = Uri.decode(backStackEntry.arguments?.getString("fileName") ?: "")
            val downloadUrl = Uri.decode(backStackEntry.arguments?.getString("downloadUrl") ?: "")

            FileViewerScreen(
                fileName = fileName,
                downloadUrl = downloadUrl,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
