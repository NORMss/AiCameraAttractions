package com.norm.aicameraattractions.presentation.camera.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ContextualFlowRowOverflow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.norm.aicameraattractions.domain.model.DownloadState
import com.norm.aicameraattractions.domain.model.Region
import com.norm.aicameraattractions.presentation.DEFAULT_MAX_LINES
import com.norm.aicameraattractions.presentation.gallery.GalleryState
import com.norm.aicameraattractions.presentation.gallery.components.DownloadButton
import com.norm.aicameraattractions.presentation.gallery.components.DownloadedButton
import com.norm.aicameraattractions.presentation.gallery.components.NotDownloadedButton
import com.norm.aicameraattractions.presentation.small_padding

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegionSelectorFlowRow(
    modifier: Modifier = Modifier,
    regions: List<Region>,
    selectedRegion: Region,
    onRegionSelect: (Region) -> Unit,
    onStartDownload: (Region) -> Unit,
) {
    var maxLines by remember {
        mutableIntStateOf(DEFAULT_MAX_LINES)
    }

    val context = LocalContext.current

    ContextualFlowRow(
        modifier = modifier
            .animateContentSize()
            .padding(),
        itemCount = regions.size,
        maxLines = maxLines,
        overflow = ContextualFlowRowOverflow.expandOrCollapseIndicator(
            expandIndicator = {
                TextButton(
                    onClick = {
                        maxLines += 1
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                ) {
                    Text(
                        text = "${this@expandOrCollapseIndicator.totalItemCount - this@expandOrCollapseIndicator.shownItemCount}+ more"
                    )
                }
            },
            collapseIndicator = {
                TextButton(
                    onClick = {
                        maxLines = DEFAULT_MAX_LINES
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(
                            ButtonDefaults.IconSize,
                        ),
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                    )
                }
            },
        ),
        horizontalArrangement = Arrangement.spacedBy(small_padding),
    ) { index ->
//        LaunchedEffect(key1 = regions[index]) {
//            Toast.makeText(
//                context,
//                regions[index].downloadState.message,
//                Toast.LENGTH_LONG,
//            ).show()
//        }
        when (regions[index].downloadState) {
            is DownloadState.Downloaded -> {
                Log.d("MyLog", "${regions[index].name} ${selectedRegion.name}")
                DownloadedButton(
                    text = regions[index].name,
                    isSelected = regions[index].name == selectedRegion.name,
                    onClick = {
                        if (regions[index] != selectedRegion) {
                            onRegionSelect(regions[index])
                        }
                    },
                )
            }

            is DownloadState.Downloading -> {
                DownloadButton(
                    text = regions[index].name,
                )
                Toast.makeText(
                    context,
                    regions[index].downloadState.message,
                    Toast.LENGTH_LONG,
                ).show()
            }

            is DownloadState.NotDownloaded -> {
                NotDownloadedButton(
                    text = regions[index].name,
                    onClick = {
                        onStartDownload(regions[index])
                    },
                )
            }

            is DownloadState.Error -> {

            }
        }
    }
}

@Preview
@Composable
private fun PreviewRegionSelectorFlowRow() {
    RegionSelectorFlowRow(
        regions = listRegion,
        selectedRegion = Region(
            name = GalleryState.Regions.AFRICA.regionName,
            tfModel = "",
            downloadState = DownloadState.Downloaded("File downloaded"),
        ),
        onRegionSelect = {

        },
        onStartDownload = {

        },
    )
}

val listRegion = listOf(
    Region(
        name = GalleryState.Regions.EUROPE.regionName,
        tfModel = "",
        downloadState = DownloadState.NotDownloaded("File not downloaded"),
    ),
    Region(
        name = GalleryState.Regions.AFRICA.regionName,
        tfModel = "",
        downloadState = DownloadState.NotDownloaded("File not downloaded"),
    ),
    Region(
        name = GalleryState.Regions.ASIA.regionName,
        tfModel = "",
        downloadState = DownloadState.NotDownloaded("File not downloaded"),
    )
)