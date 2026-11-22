package com.example.githubsearch.presentation.details

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.githubsearch.R
import com.example.githubsearch.domain.model.Repository
import com.example.githubsearch.domain.model.Users
import com.example.githubsearch.presentation.common.LanguageBadge
import com.example.githubsearch.presentation.common.StateMessage
import com.example.githubsearch.presentation.theme.Dimens
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    user: Users,
    viewModel: DetailsViewModel,
    onRepoClick: (Repository) -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current
    val onIntent: (Intent) -> Unit = { viewModel.processIntent(it) }

    viewModel.collectSideEffect { effect ->
        when (effect) {
            is ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
            is ShowToastRes -> {
                Toast.makeText(context, context.getString(effect.messageResId), Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(user) {
        onIntent(LoadUserRepos(user))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(user.login) },
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
                .padding(Dimens.paddingLarge)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = user.avatarUrl.orEmpty(),
                    contentDescription = user.login,
                    placeholder = painterResource(R.drawable.baseline_portrait_24),
                    error = painterResource(R.drawable.baseline_portrait_24),
                    modifier = Modifier
                        .size(Dimens.avatarLarge)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(Dimens.paddingLarge))

                Column {
                    Text(
                        text = stringResource(R.string.name),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = user.login,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.paddingExtraLarge))

            Text(
                text = stringResource(R.string.repository),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(Dimens.paddingNormal))

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                DetailsStateContent(state = state, onRepoClick = onRepoClick)
            }
        }
    }
}

@Composable
fun DetailsStateContent(
    state: State,
    onRepoClick: (Repository) -> Unit
) {
    when {
        state.isLoading -> CircularProgressIndicator()
        state.repos.isNotEmpty() -> ReposList(repos = state.repos, onRepoClick = onRepoClick)
        !state.errorMessage.isNullOrBlank() -> StateMessage(
            text = state.errorMessage.ifBlank { stringResource(R.string.error_loading_repos) },
            isError = true
        )
        else -> StateMessage(text = stringResource(R.string.no_repos_found))
    }
}

@Composable
fun ReposList(
    repos: List<Repository>,
    onRepoClick: (Repository) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
    ) {
        items(repos, key = { it.id }) { repo ->
            RepoItem(repo = repo, onClick = { onRepoClick(repo) })
        }
    }
}

@Composable
fun RepoItem(
    repo: Repository,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = repo.name.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                if (!repo.language.isNullOrBlank()) {
                    LanguageBadge(language = repo.language)
                }
            }
            if (!repo.description.isNullOrBlank()) {
                Text(
                    text = repo.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenSuccessPreview() {
    com.example.githubsearch.presentation.theme.GithubSearchTheme {
        DetailsStateContent(
            state = State(
                repos = listOf(
                    Repository(id = 1, name = "Hello-World", description = "My first repo on GitHub", language = "Kotlin"),
                    Repository(id = 2, name = "Spoon-Knife", description = "Demo repository for forking", language = "JavaScript")
                )
            ),
            onRepoClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenLoadingPreview() {
    com.example.githubsearch.presentation.theme.GithubSearchTheme {
        DetailsStateContent(
            state = State(isLoading = true),
            onRepoClick = {}
        )
    }
}
