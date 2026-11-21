package com.example.githubsearch.presentation.repository

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubsearch.R
import com.example.githubsearch.domain.model.Repository
import com.example.githubsearch.presentation.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryScreen(
    repository: Repository,
    onBackClick: () -> Unit
) {
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
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(Dimens.paddingLarge)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.paddingLarge),
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingNormal)
            ) {
                DetailRow(label = stringResource(R.string.repository_name), value = repository.name)
                DetailRow(label = stringResource(R.string.full_name), value = repository.fullName)
                DetailRow(label = stringResource(R.string.language), value = repository.language)
                DetailRow(label = stringResource(R.string.forks_count), value = repository.forksCount.toString())
                DetailRow(label = stringResource(R.string.description), value = repository.description)
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepositoryScreenPreview() {
    com.example.githubsearch.presentation.theme.GithubSearchTheme {
        RepositoryScreen(
            repository = Repository(
                id = 1,
                name = "Github_Search",
                fullName = "octocat/Github_Search",
                language = "Kotlin",
                forksCount = 42,
                description = "Android GitHub Search App with Clean Architecture & Compose"
            ),
            onBackClick = {}
        )
    }
}
