package com.norm.aicameraattractions.data.local

import android.net.Uri
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.norm.aicameraattractions.domain.model.Landmark
import kotlinx.coroutines.flow.Flow

@Dao
interface LandmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(landmark: Landmark)

    @Delete
    suspend fun delete(landmark: Landmark)

    @Query("SELECT * FROM landmarks")
    fun selectLandmarks(): Flow<List<Landmark>>

    @Query("SELECT * FROM landmarks WHERE imagePath=:imagePath ")
    suspend fun selectLandmark(imagePath: Uri): Landmark?

}