package com.norm.aicameraattractions.domain.usecases.camerausecases

import android.graphics.Bitmap
import android.net.Uri
import com.norm.aicameraattractions.domain.repository.CameraRepository

class SavePhoto(
    private val cameraRepository: CameraRepository,
) {
    suspend operator fun invoke(bitmap: Bitmap): Uri? {
        return cameraRepository.savePhoto(bitmap)
    }
}