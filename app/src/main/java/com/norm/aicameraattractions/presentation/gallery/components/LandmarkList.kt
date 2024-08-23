package com.norm.aicameraattractions.presentation.gallery.components

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.norm.aicameraattractions.domain.model.Landmark
import com.norm.aicameraattractions.presentation.medium_rounded
import com.norm.aicameraattractions.presentation.min_size_width_column
import com.norm.aicameraattractions.presentation.small_padding
import kotlinx.coroutines.delay

@Composable
fun LandmarkList(
    modifier: Modifier = Modifier,
    landmarks: List<Landmark>,
    onDetailsClick: (Uri) -> Unit,
) {
    var isLoading by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        delay(2000L)
        isLoading = true
    }
    val handleResult = handleResult(
        modifier = modifier,
        landmarks = landmarks,
        isLoading = isLoading,
    )
    if (handleResult) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(min_size_width_column),
            modifier = modifier,
            contentPadding = PaddingValues(small_padding),
            horizontalArrangement = Arrangement.spacedBy(small_padding),
            verticalItemSpacing = small_padding,
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
    }
}

@Composable
private fun ShimmerLandmarkList(
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(min_size_width_column),
        modifier = modifier,
        contentPadding = PaddingValues(small_padding),
        horizontalArrangement = Arrangement.spacedBy(small_padding),
        verticalItemSpacing = small_padding,
    ) {
        items(12) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f)
                    .clip(RoundedCornerShape(medium_rounded))
                    .shimmerEffect(),
            )
        }
    }
}

@Composable
fun handleResult(
    modifier: Modifier = Modifier,
    landmarks: List<Landmark>,
    isLoading: Boolean = true,
): Boolean {
    return when {
        landmarks.isEmpty() && !isLoading -> {
            ShimmerLandmarkList(
                modifier = modifier,
            )
            false
        }

        landmarks.isEmpty() && isLoading -> {
            EmptyLandmarksList(
                modifier = modifier,
            )
            false
        }

        else -> {
            true
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
private fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition(label = "")
    val alpha = transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Restart,
        ), label = ""
    ).value
    background(color = Color.Gray.copy(alpha = alpha))
}

//private val listLandmarks = listOf(
//    Landmark(
//        imagePath = "file:///storage/emulated/0/DCIM/Camera/IMG_20240515_182522446.jpg",
//        landmarkName = "Louvre",
//        region = "Europe"
//    ),
//    Landmark(
//        imagePath = "file:///storage/emulated/0/DCIM/Camera/IMG_20240515_182522446.jpg",
//        landmarkName = "Louvre",
//        region = "Europe"
//    ),
//    Landmark(
//        imagePath = "file:///storage/emulated/0/DCIM/Camera/IMG_20240515_182522446.jpg",
//        landmarkName = "Louvre",
//        region = "Europe"
//    ),
//    Landmark(
//        imagePath = "file:///storage/emulated/0/DCIM/Camera/IMG_20240515_182522446.jpg",
//        landmarkName = "Louvre",
//        region = "Europe"
//    ),
//    Landmark(
//        imagePath = "file:///storage/emulated/0/DCIM/Camera/IMG_20240515_182522446.jpg",
//        landmarkName = "Louvre",
//        region = "Europe"
//    ),
//)

val listAttractionsEmpty = emptyList<Landmark>()

//@Preview
//@Composable
//private fun PreviewGalleryScreen() {
//    LandmarkList(
//        landmarks = listLandmarks,
//    ) {
//
//    }
//}

@Preview
@Composable
private fun PreviewEmptyGalleryScreen() {
    LandmarkList(
        landmarks = listAttractionsEmpty,
    ) {

    }
}