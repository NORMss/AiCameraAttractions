package com.norm.aicameraattractions.domain.usecases.landmarkusecases

import android.net.Uri
import com.norm.aicameraattractions.domain.model.Landmark
import com.norm.aicameraattractions.domain.repository.LandmarkRepository

class SelectLandmark(
    private val landmarkRepository: LandmarkRepository,
) {
    suspend operator  fun invoke(imagePath: Uri): Landmark? {
        return landmarkRepository.selectLandmark(imagePath)
    }
}