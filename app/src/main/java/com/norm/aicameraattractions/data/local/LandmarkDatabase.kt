package com.norm.aicameraattractions.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.norm.aicameraattractions.domain.model.Landmark

@Database(
    entities = [Landmark::class], version = 2
)
@TypeConverters(UriTypeConverter::class)
abstract class LandmarkDatabase : RoomDatabase() {
    abstract val landmarkDao: LandmarkDao
}