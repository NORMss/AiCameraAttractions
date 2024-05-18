package com.norm.aicameraattractions.presentation.camera

import android.net.Uri
import com.norm.aicameraattractions.model.Classification

data class CameraState(
    val classifications: List<Classification> = emptyList(),
    val imageUri: Uri? = null,
)
