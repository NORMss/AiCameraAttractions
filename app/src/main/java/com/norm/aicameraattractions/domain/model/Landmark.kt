package com.norm.aicameraattractions.domain.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "landmarks"
)
data class Landmark(
    @PrimaryKey val imagePath: Uri,
    val landmarkName: String,
    val region: String,
)
