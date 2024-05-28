package com.norm.aicameraattractions.model.repository

import android.net.Uri
import com.norm.aicameraattractions.model.Landmark
import kotlinx.coroutines.flow.Flow

interface LandmarkRepository {

    suspend fun upsert(landmark: Landmark)
    suspend fun delete(landmark: Landmark)
    fun selectLandmarks(): Flow<List<Landmark>>
    suspend fun selectLandmark(imagePath: Uri): Landmark?
}