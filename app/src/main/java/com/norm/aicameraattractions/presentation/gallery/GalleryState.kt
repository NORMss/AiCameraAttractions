package com.norm.aicameraattractions.presentation.gallery

import com.norm.aicameraattractions.model.Landmark

data class GalleryState(
    val landmarksList: List<Landmark> = emptyList(),
    val filterRegionMap: Map<String, Boolean> = emptyMap(),
    val isActiveSearch: Boolean = false,
    val searchText: String = "",
) {
    enum class Regions(val regionName: String) {
        AFRICA("Africa"), ASIA("Asia"), EUROPE("Europe"), NORTH_AMERICA("North America"), ANTARCTICA(
            "Antarctica"
        ),
        SOUTH_AMERICA("South America"),
    }
}
