package com.norm.aicameraattractions.presentation.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.presentation.gallery.components.LandmarkCard
import com.norm.aicameraattractions.presentation.gallery.components.EmptyLandmarksList
import com.norm.aicameraattractions.presentation.medium_padding
import com.norm.aicameraattractions.presentation.min_size_width_column
import com.norm.aicameraattractions.presentation.smale_padding

@Composable
fun GalleryScreen(
    landmarks: List<Landmark>,
    onOpenCamera: () -> Unit,
    onDetailsClick: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    onOpenCamera()
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraEnhance,
                        contentDescription = "Open Camera",
                    )
                    Text(
                        text = "Open Camera"
                    )
                }
            }
        }
    ) { padding ->
        if (landmarks.isNotEmpty()) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(min_size_width_column),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = padding.calculateBottomPadding(),
                        top = padding.calculateTopPadding(),
                    )
                    .padding(horizontal = medium_padding),
                contentPadding = PaddingValues(smale_padding),
                horizontalArrangement = Arrangement.spacedBy(smale_padding),
                verticalItemSpacing = smale_padding,
            ) {
                items(landmarks) { item ->
                    LandmarkCard(
                        landmark = item,
                        onClick = {
                            onDetailsClick(item.imagePath)
                        }
                    )
                }
            }
        } else {
            EmptyLandmarksList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = padding.calculateBottomPadding(),
                        top = padding.calculateTopPadding(),
                    )
            )
        }
    }
}

val listLandmarks = listOf(
    Landmark(
        imagePath = "file:///storage/emulated/0/DCIM/Camera/IMG_20240515_182522446.jpg",
        landmarkName = "Louvre",
        region = "Europe"
    ),
    Landmark(
        imagePath = "file:///storage/emulated/0/DCIM/Camera/IMG_20240515_182522446.jpg",
        landmarkName = "Louvre",
        region = "Europe"
    ),
    Landmark(
        imagePath = "file:///storage/emulated/0/DCIM/Camera/IMG_20240515_182522446.jpg",
        landmarkName = "Louvre",
        region = "Europe"
    ),
    Landmark(
        imagePath = "file:///storage/emulated/0/DCIM/Camera/IMG_20240515_182522446.jpg",
        landmarkName = "Louvre",
        region = "Europe"
    ),
    Landmark(
        imagePath = "file:///storage/emulated/0/DCIM/Camera/IMG_20240515_182522446.jpg",
        landmarkName = "Louvre",
        region = "Europe"
    ),
)

val listAttractionsEmpty = emptyList<Landmark>()

@Preview
@Composable
fun PreviewGalleryScreen() {
    GalleryScreen(
        landmarks = listLandmarks,
        onOpenCamera = {

        },
        {

        }
    )
}

@Preview
@Composable
fun PreviewEmptyGalleryScreen() {
    GalleryScreen(
        landmarks = listAttractionsEmpty,
        onOpenCamera = {

        },
        {

        }
    )
}