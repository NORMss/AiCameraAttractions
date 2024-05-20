package com.norm.aicameraattractions.presentation.camera.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.norm.aicameraattractions.model.Classification
import com.norm.aicameraattractions.presentation.medium_padding
import com.norm.aicameraattractions.presentation.medium_rounded

@Composable
fun LandmarkNameCard(
    classification: Classification,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .clip(RoundedCornerShape(medium_rounded))
            .background(
                when (classification.score) {
                    in 0f..0.55f -> {
                        MaterialTheme.colorScheme.errorContainer
                    }

                    in 0.55f..0.62f -> {
                        MaterialTheme.colorScheme.tertiaryContainer
                    }

                    in 0.62f..1f -> {
                        MaterialTheme.colorScheme.primaryContainer
                    }

                    else -> {
                        MaterialTheme.colorScheme.surfaceContainer
                    }
                }.copy(alpha = 0.5f)
            )
            .padding(medium_padding),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = classification.name,
            color = when (classification.score) {
                in 0f..0.55f -> {
                    MaterialTheme.colorScheme.onErrorContainer
                }

                in 0.55f..0.62f -> {
                    MaterialTheme.colorScheme.onTertiaryContainer
                }

                in 0.62f..1f -> {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }

                else -> {
                    MaterialTheme.colorScheme.onSurface
                }
            },
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun PreviewLandmarkNameCard1() {
    LandmarkNameCard(
        classification = Classification(
            name = "dqwdqwd",
            score = 0.5f,
        )
    )
}

@Preview
@Composable
fun PreviewLandmarkNameCard2() {
    LandmarkNameCard(
        classification = Classification(
            name = "fsdwfhjdshfbsjadbfhjs dsfbsdbfjj  jsdbfjsbdjfbjh jdshfbjsdbjfb sjdfbjsedbfjhs",
            score = 0.6f,
        )
    )
}

@Preview
@Composable
fun PreviewLandmarkNameCard3() {
    LandmarkNameCard(
        classification = Classification(
            name = "fsdwfdbfhjs dsfbsdbfjj  jsdbfj",
            score = 0.7f,
        )
    )
}

