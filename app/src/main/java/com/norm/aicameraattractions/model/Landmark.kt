package com.norm.aicameraattractions.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "landmarks"
)
data class Landmark(
    @PrimaryKey val imagePath: String,
    val landmarkName: String,
    val region: String,
)
