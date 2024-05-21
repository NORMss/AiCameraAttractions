package com.norm.aicameraattractions.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.aicameraattractions.model.usecases.landmarkusecases.LandmarkUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val landmarkUseCases: LandmarkUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    fun getLandmark(uri: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectLandmark = landmarkUseCases.selectLandmark(
                        imagePath = uri,
                    )
                )
            }
        }
    }
}