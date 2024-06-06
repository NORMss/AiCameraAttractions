package com.norm.aicameraattractions.domain.usecases.landmarkusecases

import com.norm.aicameraattractions.domain.model.Landmark
import com.norm.aicameraattractions.domain.repository.LandmarkRepository

class UpsertLandmark(
    private val landmarkRepository: LandmarkRepository,
) {
    suspend operator  fun invoke(landmark: Landmark) {
        return landmarkRepository.upsert(landmark)
    }
}