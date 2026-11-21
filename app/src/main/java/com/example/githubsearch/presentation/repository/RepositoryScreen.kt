package com.example.githubsearch.presentation.repository

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubsearch.R
import com.example.githubsearch.domain.model.RepoContentItem
import com.example.githubsearch.domain.model.Repository
import com.example.githubsearch.presentation.common.StateMessage
import com.example.githubsearch.presentation.theme.Dimens
import com.example.githubsearch.presentation.theme.GithubSearchTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryScreen(
    repository: Repository,
    viewModel: RepositoryViewModel = hiltViewModel(),
    onFileClick: (RepoContentItem) -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect { effect ->
        when (effect) {
            is RepositoryContract.SideEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
            is RepositoryContract.SideEffect.ShowToastRes -> {
                Toast.makeText(context, context.getString(effect.messageResId), Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(repository) {
        if (state.contents.isEmpty() && !state.isLoading && state.currentPath.isBlank()) {
            viewModel.loadContents(repository, "", pushToStack = false)
        }
    }

    RepositoryContent(
        repository = repository,
        state = state,
        onItemClick = { item ->
            when {
                item.type == "dir" -> viewModel.loadContents(repository, item.path, pushToStack = true)
                !item.downloadUrl.isNullOrBlank() -> onFileClick(item)
            }
        },
        onBackClick = {
            if (!viewModel.onBackPressed()) {
                onBackClick()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryContent(
    repository: Repository,
    state: RepositoryContract.State,
    onItemClick: (RepoContentItem) -> Unit,
    onBackClick: () -> Unit
) {
    val sortedContents = remember(state.contents) {
        state.contents.sortedWith(
            compareBy<RepoContentItem> { if (it.type == "dir") 0 else 1 }
                .thenBy { it.name.lowercase() }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(repository.name.orEmpty()) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Dimens.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
        ) {
            OutlinedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.paddingNormal),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = stringResource(R.string.repo_full_name, repository.fullName.orEmpty()), style = MaterialTheme.typography.bodyMedium)
                    if (!repository.language.isNullOrBlank()) {
                        Text(text = stringResource(R.string.repo_language, repository.language), style = MaterialTheme.typography.bodyMedium)
                    }
                    Text(text = stringResource(R.string.repo_forks, repository.forksCount), style = MaterialTheme.typography.bodyMedium)
                    if (!repository.description.isNullOrBlank()) {
                        Text(text = stringResource(R.string.repo_description, repository.description), style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimens.paddingSmall))

            Text(
                text = if (state.currentPath.isBlank()) {
                    stringResource(R.string.contents_root)
                } else {
                    stringResource(R.string.contents_path, state.currentPath)
                },
                style = MaterialTheme.typography.titleMedium
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.isLoading -> CircularProgressIndicator()
                    sortedContents.isNotEmpty() -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
                        ) {
                            items(sortedContents, key = { it.path }) { item ->
                                RepoContentRow(item = item, onItemClick = { onItemClick(item) })
                            }
                        }
                    }
                    !state.errorMessage.isNullOrBlank() -> StateMessage(
                        text = state.errorMessage,
                        isError = true
                    )
                    else -> StateMessage(text = stringResource(R.string.directory_empty))
                }
            }
        }
    }
}

@Composable
fun RepoContentRow(
    item: RepoContentItem,
    onItemClick: () -> Unit
) {
    val icon = when (item.type) {
        "dir" -> Icons.Default.Folder
        else -> Icons.AutoMirrored.Filled.InsertDriveFile
    }
    val tint = when (item.type) {
        "dir" -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.secondary
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(horizontal = Dimens.paddingNormal, vertical = Dimens.paddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = item.type,
                tint = tint
            )

            Spacer(modifier = Modifier.width(Dimens.paddingLarge))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (item.type == "file" && item.size != null) {
                    Text(
                        text = stringResource(R.string.file_size_bytes, item.size),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepositoryScreenPreview() {
    GithubSearchTheme {
        RepositoryContent(
            repository = Repository(
                id = 1,
                name = "Github_Search",
                fullName = "octocat/Github_Search",
                language = "Kotlin",
                forksCount = 42,
                description = "Android GitHub Search App with Clean Architecture & Compose"
            ),
            state = RepositoryContract.State(
                contents = listOf(
                    RepoContentItem(name = "README.md", path = "README.md", type = "file", size = 1024, downloadUrl = "https://example.com"),
                    RepoContentItem(name = "app", path = "app", type = "dir"),
                    RepoContentItem(name = "gradle", path = "gradle", type = "dir")
                )
            ),
            onItemClick = {},
            onBackClick = {}
        )
    }
}
