package com.norm.aicameraattractions.presentation.gallery.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.presentation.medium_rounded
import com.norm.aicameraattractions.presentation.smale_padding
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LandmarkCard(
    landmark: Landmark,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f / 4f)
            .clip(RoundedCornerShape(medium_rounded))
            .clickable {
                onClick()
            },
    ) {
        AsyncImage(
            model = Uri.parse(landmark.imagePath),
            contentDescription = landmark.landmarkName,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
                .padding(horizontal = smale_padding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = landmark.landmarkName,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

val landmark = Landmark(
    imagePath = "file:///storage/emulated/0/DCIM/Camera/IMG_20240515_182522446.jpg",
    landmarkName = "Louvre",
    region = "Europe"
)

@Preview
@Composable
fun PreviewAttractionCard() {
    LandmarkCard(
        landmark = landmark,
        onClick = {},
    )
}