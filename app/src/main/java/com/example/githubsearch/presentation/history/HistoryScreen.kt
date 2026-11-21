package com.example.githubsearch.presentation.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubsearch.R
import com.example.githubsearch.domain.model.Users
import com.example.githubsearch.presentation.theme.Dimens
import com.example.githubsearch.presentation.users.UserItem
import org.orbitmvi.orbit.compose.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onUserClick: (Users) -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.collectAsState()

    HistoryContent(
        users = state.users,
        onUserClick = onUserClick,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryContent(
    users: List<Users>,
    onUserClick: (Users) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.history_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Dimens.paddingLarge),
            contentAlignment = Alignment.Center
        ) {
            if (users.isEmpty()) {
                Text(
                    text = stringResource(R.string.history_empty),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
                ) {
                    items(users, key = { it.id }) { user ->
                        UserItem(user = user, onClick = { onUserClick(user) })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    com.example.githubsearch.presentation.theme.GithubSearchTheme {
        HistoryContent(
            users = listOf(
                Users(id = 1, login = "octocat"),
                Users(id = 2, login = "torvalds")
            ),
            onUserClick = {},
            onBackClick = {}
        )
    }
}
