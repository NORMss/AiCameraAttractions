package com.norm.aicameraattractions.model.usecases.landmarkusecases

import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.model.repository.LandmarkRepository

class DeleteLandmark(
    private val landmarkRepository: LandmarkRepository,
) {
    suspend operator fun invoke(landmark: Landmark) {
        landmarkRepository.delete(landmark)
    }
}