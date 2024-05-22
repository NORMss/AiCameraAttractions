@file:OptIn(ExperimentalMaterial3Api::class)

package com.norm.aicameraattractions.presentation.camera.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.norm.aicameraattractions.model.Region

@Composable
fun RegionSelect(
    regions: List<Region>,
    selectedRegion: Region,
    onRegionSelect: (Region) -> Unit,
) {
    var isExpend by remember {
        mutableStateOf(false)
    }
    var region by remember {
        mutableStateOf(selectedRegion.name)
    }

    ExposedDropdownMenuBox(
        expanded = isExpend,
        onExpandedChange = {
            isExpend = it
        }
    ) {
        TextField(
            value = region,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpend)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor(),
        )
        ExposedDropdownMenu(
            expanded = isExpend,
            onDismissRequest = {
                isExpend = false
            }
        ) {
            regions.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it.name,
                        )
                    },
                    onClick = {
                        region = it.name
                        onRegionSelect(it)
                        isExpend = false
                    }
                )
            }
        }
    }
}