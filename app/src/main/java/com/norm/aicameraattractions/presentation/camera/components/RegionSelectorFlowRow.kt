package com.norm.aicameraattractions.presentation.camera.components

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
import androidx.compose.ui.tooling.preview.Preview
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
) {
    var maxLines by remember {
        mutableIntStateOf(DEFAULT_MAX_LINES)
    }

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
                onRegionSelect(regions[index])
            },
            colors = if (regions[index] == selectedRegion) ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ) else ButtonDefaults.buttonColors()
        ) {
            Text(
                text = regions[index].name
            )
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
        ),
        onRegionSelect = {

        },
    )
}

val listRegion = listOf(
    Region(
        name = GalleryState.Regions.EUROPE.regionName,
        tfModel = "",
    ),
    Region(
        name = GalleryState.Regions.AFRICA.regionName,
        tfModel = "",
    ),
    Region(
        name = GalleryState.Regions.ASIA.regionName,
        tfModel = "",
    )
)