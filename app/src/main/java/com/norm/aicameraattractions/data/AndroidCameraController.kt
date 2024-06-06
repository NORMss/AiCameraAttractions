package com.norm.aicameraattractions.data

import android.content.Context
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.norm.aicameraattractions.domain.model.CameraController
import com.norm.aicameraattractions.domain.model.Classification
import com.norm.aicameraattractions.domain.usecases.camerausecases.CameraUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AndroidCameraController(
    private val context: Context,
    private val cameraUseCases: CameraUseCases,
) : CameraController {
    private val _classifications = MutableStateFlow<List<Classification>>(emptyList())
    override val classifications: StateFlow<List<Classification>>
        get() = _classifications.asStateFlow()

    private val _imagePath = MutableStateFlow<Uri>(Uri.parse(""))
    override val imagePath: StateFlow<Uri>
        get() = _imagePath.asStateFlow()

    private val analyzer = LandmarkImageAnalyzer(
        classifier = LandmarkClassifierImpl(
            context = context
        ),
        onResults = {
            _classifications.update { it }
        }
    )

    private val _controller = LifecycleCameraController(context).apply {
        setEnabledUseCases(
            androidx.camera.view.CameraController.IMAGE_CAPTURE or
                    androidx.camera.view.CameraController.IMAGE_ANALYSIS
        )
        setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(context),
            analyzer,
        )
    }


    override suspend fun takePhoto() {
        cameraUseCases.takePhoto(_controller, {})
    }

    override fun cameraSelector() {
        _controller.cameraSelector =
            if (_controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
    }
}