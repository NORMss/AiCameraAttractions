package com.norm.aicameraattractions.presentation.gallery

import com.norm.aicameraattractions.model.Landmark

data class GalleryState(
    val landmarksList: List<Landmark> = emptyList(),
)
