package com.norm.aicameraattractions.model.usecases.landmarkusecases

import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.model.repository.LandmarkRepository

class SelectLandmark(
    private val landmarkRepository: LandmarkRepository,
) {
    suspend operator  fun invoke(imagePath: String): Landmark? {
        return landmarkRepository.selectLandmark(imagePath)
    }
}