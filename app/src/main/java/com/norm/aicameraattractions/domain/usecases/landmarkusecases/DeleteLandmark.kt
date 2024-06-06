package com.norm.aicameraattractions.domain.usecases.landmarkusecases

import com.norm.aicameraattractions.domain.model.Landmark
import com.norm.aicameraattractions.domain.repository.LandmarkRepository

class DeleteLandmark(
    private val landmarkRepository: LandmarkRepository,
) {
    suspend operator fun invoke(landmark: Landmark) {
        landmarkRepository.delete(landmark)
    }
}