package com.norm.aicameraattractions.domain.usecases.camerausecases

import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import com.norm.aicameraattractions.domain.repository.CameraRepository

class TakePhoto(
    private val cameraRepository: CameraRepository,
) {
    suspend operator fun invoke(
        controller: LifecycleCameraController,
        onPhotoCaptured: (Bitmap) -> Unit,
    ) {
        cameraRepository.takePhoto(
            controller = controller,
            onPhotoCaptured = {
                onPhotoCaptured(it)
            }
        )
    }
}