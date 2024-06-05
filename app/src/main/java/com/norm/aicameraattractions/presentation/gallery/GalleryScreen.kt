@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.norm.aicameraattractions.presentation.gallery

import android.net.Uri
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.presentation.gallery.components.LandmarkList
import com.norm.aicameraattractions.presentation.medium_padding
import com.norm.aicameraattractions.presentation.smale_padding

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
            TopAppBar(
                title = {
                    Text(
                        text = "Gallery"
                    )
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding(),
                )
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = medium_padding
                    ),
                query = state.searchText,
                onQueryChange = {
                    onChangeSearchText(it)
                },
                onSearch = {
                    onSearchChange(false)
                },
                placeholder = {
                    Text(
                        text = "Search..."
                    )
                },
                active = state.isActiveSearch,
                onActiveChange = {
                    onSearchChange(it)
                },
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(medium_padding),
                    contentPadding = PaddingValues(smale_padding)
                ) {
                    items(landmarks) { landmark ->
                        Text(
                            text = landmark.landmarkName,
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