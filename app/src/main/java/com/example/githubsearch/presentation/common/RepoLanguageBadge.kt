package com.example.githubsearch.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.githubsearch.presentation.theme.Dimens

@Composable
fun LanguageBadge(language: String) {
    val color = remember(language) { ProgrammingLanguage.getColorForLanguage(language) }

    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = Dimens.badgePaddingHorizontal,
                vertical = Dimens.badgePaddingVertical
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.badgeSpacing)
        ) {
            Box(
                modifier = Modifier
                    .size(Dimens.badgeDotSize)
                    .clip(CircleShape)
                    .background(color)
            )
            Text(
                text = language,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
