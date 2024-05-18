package com.norm.aicameraattractions.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.norm.aicameraattractions.model.Landmark

@Database(
    entities = [Landmark::class], version = 1
)
abstract class LandmarkDatabase : RoomDatabase() {
    abstract val landmarkDao: LandmarkDao
}