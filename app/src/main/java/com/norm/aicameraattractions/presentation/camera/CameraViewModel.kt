package com.norm.aicameraattractions.presentation.camera

import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.model.repository.CameraRepository
import com.norm.aicameraattractions.model.usecases.landmarkusecases.LandmarkUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val cameraRepository: CameraRepository,
    private val landmarkUseCases: LandmarkUseCases,
) : ViewModel() {
    private val _state = mutableStateOf(CameraState())
    val state: State<CameraState> = _state

    fun onTakePhoto(controller: LifecycleCameraController) {
        viewModelScope.launch {
            cameraRepository.takePhoto(controller)
            landmarkUseCases.upsertLandmark(
                Landmark(
                    "file:///storage/emulated/0/DCIM/Camera/IMG_20240515_182522446.jpg",
                    "test",
                    ","
                )
            )
        }
    }
}