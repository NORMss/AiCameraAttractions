package com.norm.aicameraattractions.domain.repository

import android.graphics.Bitmap
import android.net.Uri
import androidx.camera.view.LifecycleCameraController

interface CameraRepository {
    suspend fun takePhoto(
        controller: LifecycleCameraController,
        onPhotoCaptured: (Bitmap) -> Unit,
    )

    suspend fun savePhoto(
        bitmap: Bitmap,
    ): Uri?
}