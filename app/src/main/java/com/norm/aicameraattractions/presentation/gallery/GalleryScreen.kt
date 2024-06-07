@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.norm.aicameraattractions.presentation.gallery

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.norm.aicameraattractions.domain.model.Landmark
import com.norm.aicameraattractions.presentation.gallery.components.LandmarkList
import com.norm.aicameraattractions.presentation.medium_padding
import com.norm.aicameraattractions.presentation.medium_rounded
import com.norm.aicameraattractions.presentation.smale_image
import com.norm.aicameraattractions.presentation.smale_padding
import com.norm.aicameraattractions.presentation.smale_rounded

@Composable
fun GalleryScreen(
    state: GalleryState,
    landmarks: List<Landmark>,
    onOpenCamera: () -> Unit,
    onDetailsClick: (Uri) -> Unit,
    selectFilter: (String) -> Unit,
    onSearchChange: (Boolean) -> Unit,
    onChangeSearchText: (String) -> Unit,
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
        },
        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "Gallery"
//                    )
//                },
//            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding(),
                ),
        ) {
            SearchBar(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .semantics { traversalIndex = 0f },
                inputField = {
                    SearchBarDefaults.InputField(
                        query = state.searchText,
                        onQueryChange = {
                            onChangeSearchText(it)
                        },
                        onSearch = {
                            onSearchChange(false)
                        },
                        expanded = state.isActiveSearch,
                        onExpandedChange = {
                            onSearchChange(it)
                        },
                        placeholder = {
                            Text(
                                text = "Search..."
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    )
                },
                expanded = state.isActiveSearch,
                onExpandedChange = {
                    onSearchChange(false)
                },
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        vertical = smale_padding,
                        horizontal = medium_padding,
                    ),
                    verticalArrangement = Arrangement.spacedBy(smale_padding),
                ) {
                    items(landmarks) { landmark ->
                        ItemSearchResult(
                            landmark,
                            onClick = {
                                onSearchChange(false)
                                onDetailsClick(landmark.imagePath)
                            }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(
                        state = rememberScrollState(),
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(smale_padding),
            ) {
                state.filterRegionMap.forEach {
                    FilterChip(
                        onClick = {
                            selectFilter(
                                it.key
                            )
                        },
                        label = {
                            Text(
                                text = it.key,
                                maxLines = 1,
                            )
                        },
                        selected = it.value,
                        leadingIcon = if (it.value) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        },
                    )
                }
            }
            LandmarkList(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = medium_padding),
                landmarks = landmarks
            ) {
                onDetailsClick(it)
            }
        }
    }
}

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
                    .size(smale_image)
                    .clip(RoundedCornerShape(smale_rounded)),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewItemSearchResult() {
    ItemSearchResult(
        landmark = Landmark(
            Uri.parse(""),
            "NOVAT – Novosibirsk State Academic Theater of Opera and Ballet",
            "Europe",
        ),
        onClick = {

        }
    )
}