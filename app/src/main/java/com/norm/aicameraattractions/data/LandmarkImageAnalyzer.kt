package com.norm.aicameraattractions.data

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.norm.aicameraattractions.domain.model.Classification
import com.norm.aicameraattractions.domain.model.LandmarkClassifier
import com.norm.aicameraattractions.util.centerCrop

class LandmarkImageAnalyzer(
    private val classifier: LandmarkClassifier,
    private val onResults: (List<Classification>) -> Unit,
    private val modelPath: String = "classifier-europe-v1.tflite",
    private val maxResults: Int = 1,
    private val threshold: Float = 0.5f
) : ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if (frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centerCrop(321, 321)

            val results = classifier.classify(
                bitmap = bitmap,
                rotation = rotationDegrees,
                modelPath = modelPath,
                maxResults = maxResults,
                threshold = threshold,
            )
            onResults(results)
        }

        frameSkipCounter++

        image.close()
    }
}