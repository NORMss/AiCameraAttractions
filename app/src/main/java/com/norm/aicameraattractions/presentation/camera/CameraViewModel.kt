package com.norm.aicameraattractions.presentation.camera

import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.aicameraattractions.domain.model.Classification
import com.norm.aicameraattractions.domain.model.DownloadState
import com.norm.aicameraattractions.domain.model.Landmark
import com.norm.aicameraattractions.domain.model.Region
import com.norm.aicameraattractions.domain.usecases.camerausecases.CameraUseCases
import com.norm.aicameraattractions.domain.usecases.landmarkusecases.LandmarkUseCases
import com.norm.aicameraattractions.presentation.gallery.GalleryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val cameraUseCases: CameraUseCases,
    private val landmarkUseCases: LandmarkUseCases,
) : ViewModel() {
    private val _state = mutableStateOf(CameraState())
    val state: State<CameraState> = _state

    init {
        _state.value = _state.value.copy(
            regions = listOf(
                Region(
                    name = GalleryState.Regions.EUROPE.regionName,
                    tfModel = "classifier-europe-v1.tflite",
                    downloadState = DownloadState.Downloaded("File downloaded"),
                ),
                Region(
                    name = GalleryState.Regions.ASIA.regionName,
                    tfModel = "classifier-asia-v1.tflite",
                    downloadState = DownloadState.Downloaded("File downloaded"),

                    ),
                Region(
                    name = GalleryState.Regions.AFRICA.regionName,
                    tfModel = "classifier-africa-v1.tflite",
                    downloadState = DownloadState.Downloaded("File downloaded")
                ),
                Region(
                    name = GalleryState.Regions.NORTH_AMERICA.regionName,
                    tfModel = "classifier-north-america-v1.tflite",
                    downloadState = DownloadState.NotDownloaded("File not downloaded")
                ),
            ),
            currentRegion = Region(
                name = GalleryState.Regions.EUROPE.regionName,
                tfModel = "classifier-europe-v1.tflite",
                downloadState = DownloadState.Downloaded("File downloaded"),
            ),
        )
    }

    fun fakeStartDownload(region: Region) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                regions = _state.value.regions.map {
                    if (it == region) {
                        it.copy(
                            downloadState = DownloadState.Downloading("Starting download")
                        )
                    } else {
                        it
                    }
                }
            )
            fakeDownload(2000L)
            _state.value = _state.value.copy(
                regions = _state.value.regions.map {
                    if (it.name == region.name) {
                        it.copy(
                            downloadState = DownloadState.NotDownloaded("File not downloaded")
                        )
                    } else {
                        it
                    }
                }
            )
        }
    }

    fun setClassification(classification: List<Classification>) {
        _state.value = _state.value.copy(
            classification = classification
        )
    }

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
                        val uri = cameraUseCases.savePhoto(it) ?: Uri.parse("")
                        landmarkUseCases.upsertLandmark(
                            Landmark(
                                imagePath = uri,
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

    fun selectRegion(region: Region) {
        _state.value = _state.value.copy(
            currentRegion = region
        )
    }

    private suspend fun fakeDownload(timeMillis: Long) {
        delay(timeMillis)
        Log.d("MyLog", "fakeDownload Stop")
    }
}