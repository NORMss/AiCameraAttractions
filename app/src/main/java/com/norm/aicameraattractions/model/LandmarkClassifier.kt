package com.norm.aicameraattractions.model

import android.graphics.Bitmap

interface LandmarkClassifier {
    fun classify(
        bitmap: Bitmap,
        rotation: Int,
        threshold: Float,
        maxResults: Int,
        modelPath: String,
    ): List<Classification>
}