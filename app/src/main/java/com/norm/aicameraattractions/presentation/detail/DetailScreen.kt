package com.norm.aicameraattractions.presentation.detail

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.presentation.detail.components.DetailsTopBar
import com.norm.aicameraattractions.presentation.medium_padding
import com.norm.aicameraattractions.presentation.medium_rounded
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun DetailsScreen(
    landmark: Landmark,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            DetailsTopBar(
                title = landmark.landmarkName,
                onBackClick = {

                },
                onDeleteClick = {

                },
                onShareClick = {

                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = padding.calculateBottomPadding(),
                    top = padding.calculateTopPadding(),
                )
                .padding(medium_padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f)
                    .clip(RoundedCornerShape(medium_rounded)),
            ) {
                AsyncImage(
                    model = Uri.parse(landmark.imagePath),
                    contentDescription = landmark.landmarkName,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(
                modifier = Modifier.height(medium_padding)
            )
            Text(
                text = landmark.landmarkName,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = landmark.region,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    DetailsScreen(
        landmark = Landmark(
            imagePath = "https://res.klook.com/image/upload/c_fill,w_800/q_65/activities/aexudqopqqndey9iixnl.jpg",
            landmarkName = "Louvre",
            region = "Europe",
        )
    )
}