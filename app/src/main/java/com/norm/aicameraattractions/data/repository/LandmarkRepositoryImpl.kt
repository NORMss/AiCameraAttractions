package com.norm.aicameraattractions.data.repository

import com.norm.aicameraattractions.data.local.LandmarkDao
import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.model.repository.LandmarkRepository
import kotlinx.coroutines.flow.Flow

class LandmarkRepositoryImpl(
    private val landmarkDao: LandmarkDao,
) : LandmarkRepository {
    override suspend fun upsert(landmark: Landmark) {
        landmarkDao.upsert(landmark)
    }

    override suspend fun delete(landmark: Landmark) {
        landmarkDao.delete(landmark)
    }

    override fun selectLandmarks(): Flow<List<Landmark>> {
        return landmarkDao.selectLandmarks()
    }

    override suspend fun selectLandmark(imagePath: String): Landmark? {
        return selectLandmark(imagePath)
    }
}