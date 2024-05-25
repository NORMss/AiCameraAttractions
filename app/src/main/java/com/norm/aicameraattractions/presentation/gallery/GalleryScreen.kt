package com.norm.aicameraattractions.presentation.gallery

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.presentation.gallery.components.LandmarkList
import com.norm.aicameraattractions.presentation.medium_padding

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
        LandmarkList(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = padding.calculateBottomPadding(),
                    top = padding.calculateTopPadding(),
                )
                .padding(horizontal = medium_padding),
            landmarks = landmarks
        ) {
            onDetailsClick(it)
        }
    }
}