package com.norm.aicameraattractions.presentation.camera.components

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.norm.aicameraattractions.domain.model.DownloadState
import com.norm.aicameraattractions.domain.model.Region
import com.norm.aicameraattractions.presentation.DEFAULT_MAX_LINES
import com.norm.aicameraattractions.presentation.gallery.GalleryState
import com.norm.aicameraattractions.presentation.smale_padding

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
        horizontalArrangement = Arrangement.spacedBy(smale_padding),
    ) { index ->
        Button(
            onClick = {
                when (regions[index].downloadState) {
                    is DownloadState.Downloaded -> {
                        if (regions[index] != selectedRegion)
                            onRegionSelect(regions[index])
                    }

                    is DownloadState.NotDownloaded -> {
                        Toast.makeText(
                            context,
                            regions[index].downloadState.message,
                            Toast.LENGTH_LONG,
                        ).show()
                        onStartDownload(regions[index])
                    }

                    is DownloadState.Downloading -> {
                        Toast.makeText(
                            context,
                            regions[index].downloadState.message,
                            Toast.LENGTH_LONG,
                        ).show()
                    }

                    is DownloadState.Error -> {
                        Toast.makeText(
                            context,
                            regions[index].downloadState.message,
                            Toast.LENGTH_LONG,
                        ).show()
                    }

                }
            },
            colors = when (regions[index].downloadState) {
                is DownloadState.Downloaded -> {
                    if (regions[index] == selectedRegion) ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ) else ButtonDefaults.buttonColors()
                }

                is DownloadState.NotDownloaded -> {
                    ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    )
                }

                is DownloadState.Downloading -> {
                    ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    )
                }

                is DownloadState.Error -> {
                    ButtonDefaults.buttonColors()
                }

            }
        ) {
            when (regions[index].downloadState) {
                is DownloadState.Downloaded -> {
                    Text(
                        text = regions[index].name
                    )
                }

                is DownloadState.NotDownloaded -> {
                    Text(
                        text = regions[index].name
                    )
                }

                is DownloadState.Downloading -> {
                    Text(
                        text = regions[index].name
                    )
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(
                                ButtonDefaults.IconSize
                            ),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

                is DownloadState.Error -> {
                    Text(
                        text = regions[index].name
                    )
                }

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