package com.norm.aicameraattractions.presentation.gallery.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.norm.aicameraattractions.presentation.small_padding


@Composable
fun DownloadedButton(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        onClick = {
            onClick()
        },
        colors = if (isSelected) ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ) else ButtonDefaults.buttonColors(),
    ) {
        Text(
            text = text,
        )
    }
}

@Composable
fun NotDownloadedButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Text(
            text = text,
        )
    }
}

@Composable
fun DownloadButton(
    text: String,
) {
    Button(
        onClick = {
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Text(
            text = text,
        )
        Spacer(
            modifier = Modifier.width(small_padding)
        )
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(
                    ButtonDefaults.IconSize
                ),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}