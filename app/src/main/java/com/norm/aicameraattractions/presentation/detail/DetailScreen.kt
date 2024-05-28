package com.norm.aicameraattractions.presentation.detail

import android.content.Intent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.presentation.detail.components.DetailsTopBar
import com.norm.aicameraattractions.presentation.medium_padding
import com.norm.aicameraattractions.presentation.medium_rounded
import androidx.compose.ui.tooling.preview.Preview
import com.norm.aicameraattractions.presentation.detail.components.DeleteDialog


@Composable
fun DetailsScreen(
    landmark: Landmark,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    val context = LocalContext.current

    var isShowDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            DetailsTopBar(
                title = landmark.landmarkName,
                onBackClick = {
                    onBackClick()
                },
                onDeleteClick = {
                    isShowDialog = true
                },
                onShareClick = {
                    onShareClick()
                    val shareIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_STREAM, landmark.imagePath)
                        putExtra(Intent.EXTRA_TEXT, "Look where I've been")
                        type = "image/jpeg"
                    }
                    context.startActivity(Intent.createChooser(shareIntent, null))
                },
            )
        }
    ) { padding ->
        if (isShowDialog) {
            DeleteDialog(
                onDeleteClick = {
                    onDeleteClick()
                },
                onShowDialog = {
                    isShowDialog = it
                }
            )
        }
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
                    model = landmark.imagePath,
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
            imagePath = Uri.parse(""),
            landmarkName = "Louvre",
            region = "Europe",
        ),
        {},
        {},
        {},
    )
}