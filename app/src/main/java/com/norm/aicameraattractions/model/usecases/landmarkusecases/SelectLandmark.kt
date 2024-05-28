package com.norm.aicameraattractions.model.usecases.landmarkusecases

import android.net.Uri
import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.model.repository.LandmarkRepository

class SelectLandmark(
    private val landmarkRepository: LandmarkRepository,
) {
    suspend operator  fun invoke(imagePath: Uri): Landmark? {
        return landmarkRepository.selectLandmark(imagePath)
    }
}