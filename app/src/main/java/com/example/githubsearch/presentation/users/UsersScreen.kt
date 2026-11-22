package com.example.githubsearch.presentation.users

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.githubsearch.R
import com.example.githubsearch.domain.model.Users
import com.example.githubsearch.presentation.common.StateMessage
import com.example.githubsearch.presentation.theme.Dimens
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    viewModel: UsersViewModel,
    onUserClick: (Users) -> Unit,
    onHistoryClick: () -> Unit
) {
    val state by viewModel.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
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

    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            delay(500.milliseconds)
            onIntent(SearchUsers(searchQuery.trim()))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = onHistoryClick) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = stringResource(R.string.history)
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
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.enter_user_name)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(Dimens.cornerRadiusNormal),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                )
            )

            Spacer(modifier = Modifier.height(Dimens.paddingLarge))

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UsersStateContent(state = state, onUserClick = onUserClick)
            }
        }
    }
}

@Composable
fun UsersStateContent(
    state: State,
    onUserClick: (Users) -> Unit
) {
    when {
        state.isLoading -> CircularProgressIndicator()
        state.users.isNotEmpty() -> UsersList(users = state.users, onUserClick = onUserClick)
        !state.errorMessage.isNullOrBlank() -> StateMessage(
            text = state.errorMessage.ifBlank { stringResource(R.string.error_loading_users) },
            isError = true
        )
        else -> StateMessage(text = stringResource(R.string.no_users_found))
    }
}

@Composable
fun UsersList(
    users: List<Users>,
    onUserClick: (Users) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
    ) {
        items(users, key = { it.id }) { user ->
            UserItem(user = user, onClick = { onUserClick(user) })
        }
    }
}

@Composable
fun UserItem(
    user: Users,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingNormal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user.avatarUrl.orEmpty(),
                contentDescription = user.login,
                placeholder = painterResource(R.drawable.baseline_portrait_24),
                error = painterResource(R.drawable.baseline_portrait_24),
                modifier = Modifier
                    .size(Dimens.avatarSmall)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(Dimens.paddingLarge))

            Text(
                text = user.login,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsersScreenSuccessPreview() {
    com.example.githubsearch.presentation.theme.GithubSearchTheme {
        UsersStateContent(
            state = State(
                users = listOf(
                    Users(id = 1, login = "octocat"),
                    Users(id = 2, login = "torvalds")
                )
            ),
            onUserClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UsersScreenLoadingPreview() {
    com.example.githubsearch.presentation.theme.GithubSearchTheme {
        UsersStateContent(
            state = State(isLoading = true),
            onUserClick = {}
        )
    }
}
