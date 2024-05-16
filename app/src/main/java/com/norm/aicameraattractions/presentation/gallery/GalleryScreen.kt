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
import com.norm.aicameraattractions.R
import com.norm.aicameraattractions.model.Attraction
import com.norm.aicameraattractions.presentation.gallery.components.AttractionCard
import com.norm.aicameraattractions.presentation.gallery.components.EmptyAttractionList
import com.norm.aicameraattractions.presentation.medium_padding

@Composable
fun GalleryScreen(
    attractions: List<Attraction>,
    onOpenCamera: () -> Unit,
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
        if (attractions.isNotEmpty()) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = padding.calculateBottomPadding(),
                        top = padding.calculateTopPadding(),
                    )
                    .padding(horizontal = medium_padding),
                contentPadding = PaddingValues(medium_padding),
                horizontalArrangement = Arrangement.spacedBy(medium_padding),
                verticalItemSpacing = medium_padding,
            ) {
                items(attractions) { item ->
                    AttractionCard(
                        attraction = item,
                    )
                }
            }
        } else {
            EmptyAttractionList(
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

val listAttractions = listOf(
    Attraction(
        image = R.drawable.europe_louvre,
        description = "Louvre",
        region = "Europe"
    ),
    Attraction(
        image = R.drawable.europe_louvre,
        description = "Louvre",
        region = "Europe"
    ),
    Attraction(
        image = R.drawable.europe_louvre,
        description = "Louvre",
        region = "Europe"
    ),
    Attraction(
        image = R.drawable.europe_louvre,
        description = "Louvre",
        region = "Europe"
    ),
    Attraction(
        image = R.drawable.europe_louvre,
        description = "Louvre",
        region = "Europe"
    ),
)

val listAttractionsEmpty = emptyList<Attraction>()

@Preview
@Composable
fun PreviewGalleryScreen() {
    GalleryScreen(
        attractions = listAttractions,
        onOpenCamera = {

        }
    )
}

@Preview
@Composable
fun PreviewEmptyGalleryScreen() {
    GalleryScreen(
        attractions = listAttractionsEmpty,
        onOpenCamera = {

        }
    )
}