package com.norm.aicameraattractions.presentation.gallery.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.norm.aicameraattractions.domain.model.Landmark
import com.norm.aicameraattractions.presentation.medium_padding
import com.norm.aicameraattractions.presentation.medium_rounded
import com.norm.aicameraattractions.presentation.small_image
import com.norm.aicameraattractions.presentation.small_rounded

@Composable
fun ItemSearchResult(
    landmark: Landmark,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    medium_rounded
                )
            )
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.7f),
            )
            .clickable {
                onClick()
            }
            .padding(
                medium_padding
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = landmark.landmarkName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
            )
            AsyncImage(
                model = landmark.imagePath,
                contentDescription = null,
                modifier = Modifier
                    .size(small_image)
                    .clip(RoundedCornerShape(small_rounded)),
                contentScale = ContentScale.Crop,
            )
        }
    }
}