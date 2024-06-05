package com.norm.aicameraattractions.presentation.gallery

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.aicameraattractions.model.Landmark
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

    private var tmpLandmarkList: List<Landmark> = _state.value.landmarksList

    init {
        getLandmarks()
        Log.d("MyLog", "init")
    }

    fun selectFilter(region: String) {
        _state.value = _state.value.copy(
            filterRegionMap = _state.value.filterRegionMap.mapValues {
                if (it.key == region) {
                    !it.value
                } else
                    it.value
            }
        )
        filterLandmarks()
    }

    fun onSearchChange(active: Boolean) {
        _state.value = _state.value.copy(
            isActiveSearch = active
        )
    }

    fun onChangeSearchText(text: String) {
        _state.value = _state.value.copy(
            searchText = text
        )
        filterLandmarks()
    }

    private fun getLandmarks() {
        Log.d("MyLog", "Start getLandmarks()")
        landmarkUseCases.selectLandmarks().onEach { landmarkList ->
            _state.value = _state.value.copy(
                landmarksList = landmarkList,
                filterRegionMap = landmarkList.associate { landmark ->
                    landmark.region to (_state.value.filterRegionMap[landmark.region] ?: false)
                }
            )
            tmpLandmarkList = _state.value.landmarksList
            filterLandmarks()
            Log.d("MyLog", "End getLandmarks()")
        }.launchIn(viewModelScope)
    }

    private fun filterLandmarks() {
        Log.d("MyLog", "Start filterLandmarks")
        _state.value = _state.value.copy(
            landmarksList = tmpLandmarkList.filter { landmark ->
                landmark.region in _state.value.filterRegionMap.map {
                    when (it.value) {
                        true -> {
                            it.key
                        }

                        false -> {
                            null
                        }
                    }
                }
            }.ifEmpty {
                tmpLandmarkList
            }.filter { landmark ->
                landmark.landmarkName.lowercase().startsWith(_state.value.searchText)
            }
        )
        Log.d("MyLog", "End filterLandmarks")
        Log.d("MyLog", _state.value.filterRegionMap.toString())
    }
}