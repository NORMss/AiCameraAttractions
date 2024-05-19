package com.norm.aicameraattractions.presentation.camera

import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.model.usecases.camerausecases.CameraUseCases
import com.norm.aicameraattractions.model.usecases.landmarkusecases.LandmarkUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val cameraUseCases: CameraUseCases,
    private val landmarkUseCases: LandmarkUseCases,
) : ViewModel() {
    private val _state = mutableStateOf(CameraState())
    val state: State<CameraState> = _state

    fun onTakePhoto(
        controller: LifecycleCameraController,
        landmarkName: String,
        region: String,
    ) {
        viewModelScope.launch {
            cameraUseCases.takePhoto(
                controller = controller,
                onPhotoCaptured = {
                    viewModelScope.launch {
                        val uri = cameraUseCases.savePhoto(it)
                        landmarkUseCases.upsertLandmark(
                            Landmark(
                                imagePath = uri.toString(),
                                landmarkName = landmarkName,
                                region = region,
                            )
                        )
                    }
                }
            )
        }
    }

    fun onCameraSelector(controller: LifecycleCameraController) {
        controller.cameraSelector =
            if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
    }
}