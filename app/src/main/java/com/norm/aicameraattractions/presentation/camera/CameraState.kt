package com.norm.aicameraattractions.presentation.camera

import android.net.Uri
import com.norm.aicameraattractions.domain.model.Classification
import com.norm.aicameraattractions.domain.model.Region

data class CameraState(
    val classifications: List<Classification> = emptyList(),
    val imageUri: Uri? = null,
    val regions: List<Region> = emptyList(),
    val currentRegion: Region? = null,
    val classification: List<Classification> = emptyList()
)
