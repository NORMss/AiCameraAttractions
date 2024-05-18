package com.norm.aicameraattractions.model.usecases.landmarkusecases

import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.model.repository.LandmarkRepository
import kotlinx.coroutines.flow.Flow

class SelectLandmarks(
    private val landmarkRepository: LandmarkRepository,
) {
    operator fun invoke(): Flow<List<Landmark>> {
        return landmarkRepository.selectLandmarks()
    }
}