package com.example.githubsearch.presentation.fileviewer

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubsearch.R
import com.example.githubsearch.presentation.theme.Dimens
import com.example.githubsearch.presentation.theme.GithubSearchTheme
import com.example.githubsearch.presentation.theme.SyntaxColors
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileViewerScreen(
    fileName: String,
    downloadUrl: String,
    viewModel: FileViewerViewModel = hiltViewModel(),
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

    LaunchedEffect(downloadUrl) {
        onIntent(LoadFile(downloadUrl))
    }

    FileViewerContent(
        fileName = fileName,
        state = state,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileViewerContent(
    fileName: String,
    state: State,
    onBackClick: () -> Unit
) {
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()
    var fontScale by remember { mutableFloatStateOf(1f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(fileName, color = SyntaxColors.defaultText) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = SyntaxColors.defaultText
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SyntaxColors.topBar)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(SyntaxColors.background)
                .pointerInput(Unit) {
                    detectTransformGestures { _, _, zoom, _ ->
                        fontScale = (fontScale * zoom).coerceIn(Dimens.MIN_FONT_SCALE, Dimens.MAX_FONT_SCALE)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(color = SyntaxColors.loader)
                }
                state.content.isNotBlank() -> {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(verticalScrollState)
                            .horizontalScroll(horizontalScrollState)
                            .padding(Dimens.paddingLarge)
                    ) {
                        val lines = state.content.lines()
                        val currentFontSize = (Dimens.BASE_FONT_SIZE_SP * fontScale).sp

                        Column(
                            modifier = Modifier.padding(end = Dimens.paddingLarge),
                            horizontalAlignment = Alignment.End
                        ) {
                            lines.forEachIndexed { index, _ ->
                                Text(
                                    text = "${index + 1}",
                                    color = SyntaxColors.comment,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = currentFontSize
                                )
                            }
                        }
                        Column {
                            lines.forEach { line ->
                                Text(
                                    text = highlightCode(line.ifEmpty { " " }),
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = currentFontSize,
                                    softWrap = false
                                )
                            }
                        }
                    }
                }
                !state.errorMessage.isNullOrBlank() -> {
                    Text(
                        text = state.errorMessage,
                        color = SyntaxColors.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                state.errorMessageResId != null -> {
                    Text(
                        text = stringResource(state.errorMessageResId),
                        color = SyntaxColors.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                else -> {
                    Text(
                        text = stringResource(R.string.empty_file),
                        color = SyntaxColors.comment,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FileViewerPreview() {
    GithubSearchTheme {
        FileViewerContent(
            fileName = "MainActivity.kt",
            state = State(
                content = "package com.example\n\nfun main() {\n    val message = \"Hello, World!\"\n    println(message)\n}"
            ),
            onBackClick = {}
        )
    }
}
