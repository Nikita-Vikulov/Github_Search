package com.example.githubsearch.presentation.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StateMessage(
    text: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    Text(
        text = text,
        modifier = modifier,
        color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodyLarge
    )
}
