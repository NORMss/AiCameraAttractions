package com.norm.aicameraattractions.presentation.camera

import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.aicameraattractions.model.repository.CameraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val cameraRepository: CameraRepository,
) : ViewModel() {
    fun onTakePhoto(
        controller: LifecycleCameraController,
    ) {
        viewModelScope.launch {
            cameraRepository.takePhoto(controller)
        }
    }
}