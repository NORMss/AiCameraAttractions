package com.norm.aicameraattractions.model

import android.net.Uri
import kotlinx.coroutines.flow.StateFlow

interface CameraController {
    val classifications: StateFlow<List<Classification>>
    val imagePath: StateFlow<Uri>

    suspend fun takePhoto()
    fun cameraSelector()
}