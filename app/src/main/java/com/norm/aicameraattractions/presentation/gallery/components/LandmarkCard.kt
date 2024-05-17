package com.norm.aicameraattractions.presentation.gallery.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.presentation.medium_rounded
import com.norm.aicameraattractions.presentation.smale_padding
import androidx.compose.ui.tooling.preview.Preview
import com.norm.aicameraattractions.R

@Composable
fun AttractionCard(
    landmark: Landmark,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f / 4f)
            .clip(RoundedCornerShape(medium_rounded)),
    ) {
        Image(
            painter = painterResource(id = landmark.image),
            contentDescription = landmark.description,
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
                text = landmark.description,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
}

val landmark = Landmark(
    image = R.drawable.europe_louvre,
    description = "Louvre",
    region = "Europe"
)

@Preview
@Composable
fun PreviewAttractionCard() {
    AttractionCard(
        landmark = landmark,
    )
}