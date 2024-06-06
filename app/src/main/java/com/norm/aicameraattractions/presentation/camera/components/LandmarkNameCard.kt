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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.norm.aicameraattractions.domain.model.Classification
import com.norm.aicameraattractions.presentation.medium_padding
import com.norm.aicameraattractions.presentation.medium_rounded
import com.norm.aicameraattractions.ui.theme.Accuracy
import com.norm.aicameraattractions.ui.theme.colorForAccuracy

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
                    in 0f..0.6f -> {
                        colorForAccuracy(Accuracy.VERY_LOW)
                    }

                    in 0.61f..0.65f -> {
                        colorForAccuracy(Accuracy.LOW)
                    }

                    in 0.66f..0.72f -> {
                        colorForAccuracy(Accuracy.MEDIUM)
                    }

                    in 0.73f..1f -> {
                        colorForAccuracy(Accuracy.HIGH)
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
            color = Color.White,
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

