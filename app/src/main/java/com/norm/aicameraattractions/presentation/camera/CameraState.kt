package com.norm.aicameraattractions.presentation.camera

import android.net.Uri
import com.norm.aicameraattractions.model.Classification
import com.norm.aicameraattractions.model.Region

data class CameraState(
    val classifications: List<Classification> = emptyList(),
    val imageUri: Uri? = null,
    val currentRegion: Region = Region(
        name = "Europe",
        tfModel = "classifier-europe-v1.tflite",
    )
)
