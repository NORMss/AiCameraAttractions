package com.norm.aicameraattractions.domain.usecases.landmarkusecases

import com.norm.aicameraattractions.domain.model.Landmark
import com.norm.aicameraattractions.domain.repository.LandmarkRepository
import kotlinx.coroutines.flow.Flow

class SelectLandmarks(
    private val landmarkRepository: LandmarkRepository,
) {
    operator fun invoke(): Flow<List<Landmark>> {
        return landmarkRepository.selectLandmarks()
    }
}