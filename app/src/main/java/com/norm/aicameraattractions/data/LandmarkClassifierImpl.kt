package com.norm.aicameraattractions.data

import android.content.Context
import android.graphics.Bitmap
import com.norm.aicameraattractions.model.Classification
import com.norm.aicameraattractions.model.LandmarkClassifier
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import android.view.Surface
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage

class LandmarkClassifierImpl(
    private val context: Context,
) : LandmarkClassifier {
    private fun setupClassifier(
        threshold: Float,
        maxResults: Int,
        modelPath: String,
    ) {
        val baseOptions = BaseOptions.builder()
            .setNumThreads(2)
            .build()
        val options = ImageClassifier.ImageClassifierOptions.builder()
            .setBaseOptions(baseOptions)
            .setMaxResults(maxResults)
            .setScoreThreshold(threshold)
            .build()
        try {
            classifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelPath,
                options
            )
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }


    private var classifier: ImageClassifier? = null
    override fun classify(
        bitmap: Bitmap,
        rotation: Int,
        threshold: Float,
        maxResults: Int,
        modelPath: String,
    ): List<Classification> {
        if (classifier == null) {
            setupClassifier(
                threshold = threshold,
                maxResults = maxResults,
                modelPath = modelPath,
            )
        }

        val imageProcessor = ImageProcessor.Builder().build()
        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(bitmap))

        val imageProcessingOptions = ImageProcessingOptions.builder()
            .setOrientation(getOrientationFromRotation(rotation))
            .build()

        val results = classifier?.classify(tensorImage, imageProcessingOptions)

        return results?.flatMap { classications ->
            classications.categories.map { category ->
                Classification(
                    name = category.displayName,
                    score = category.score
                )
            }
        }?.distinctBy { it.name } ?: emptyList()

    }
}

private fun getOrientationFromRotation(rotation: Int): ImageProcessingOptions.Orientation {
    return when (rotation) {
        Surface.ROTATION_270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
        Surface.ROTATION_90 -> ImageProcessingOptions.Orientation.TOP_LEFT
        Surface.ROTATION_180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
        else -> ImageProcessingOptions.Orientation.RIGHT_TOP
    }
}