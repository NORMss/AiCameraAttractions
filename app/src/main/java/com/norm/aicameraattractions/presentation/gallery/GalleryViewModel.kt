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

    private fun getLandmarks() {
        landmarkUseCases.selectLandmarks().onEach {
            _state.value = _state.value.copy(
                landmarksList = it
            )
        }.launchIn(viewModelScope)
    }
}