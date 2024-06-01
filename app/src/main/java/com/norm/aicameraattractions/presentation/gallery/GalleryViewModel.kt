package com.norm.aicameraattractions.presentation.gallery

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.aicameraattractions.model.usecases.landmarkusecases.LandmarkUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val landmarkUseCases: LandmarkUseCases,
) : ViewModel() {
    private val _state = mutableStateOf(GalleryState())
    val state: State<GalleryState> = _state

    init {
        getLandmarks()
    }

    fun onClickFilterLandmarks(regions: Regions) {
        filterLandmarks(regions)
    }

    fun onClickNotFilter() {
        getLandmarks()
    }

    private fun getLandmarks() {
        landmarkUseCases.selectLandmarks().onEach {
            _state.value = _state.value.copy(
                landmarksList = it
            )
        }.launchIn(viewModelScope)
    }

    private fun filterLandmarks(regions: Regions) {
        _state.value = _state.value.copy(
            landmarksList = _state.value.landmarksList.filter { landmark ->
                landmark.region == regions.regionName
            }
        )
    }

    enum class Regions(val regionName: String) {
        AFRICA("Africa"), ASIA("Asia"), EUROPE("Europe"), NORTH_AMERICA("North America"), ANTARCTICA(
            "Antarctica"
        ),
        SOUTH_AMERICA("South America"),
    }
}