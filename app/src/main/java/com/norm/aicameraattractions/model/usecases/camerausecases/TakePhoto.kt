package com.norm.aicameraattractions.model.usecases.camerausecases

import androidx.camera.view.LifecycleCameraController
import com.norm.aicameraattractions.model.repository.CameraRepository

class TakePhoto(
    private val cameraRepository: CameraRepository,
) {
    suspend operator fun invoke(controller: LifecycleCameraController) {
        cameraRepository.takePhoto(controller)
    }
}