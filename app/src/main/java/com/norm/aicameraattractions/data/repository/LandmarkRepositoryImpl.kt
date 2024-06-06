package com.norm.aicameraattractions.data.repository

import android.net.Uri
import com.norm.aicameraattractions.data.local.LandmarkDao
import com.norm.aicameraattractions.domain.model.Landmark
import com.norm.aicameraattractions.domain.repository.LandmarkRepository
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

    override suspend fun selectLandmark(imagePath: Uri): Landmark? {
        return landmarkDao.selectLandmark(imagePath)
    }
}