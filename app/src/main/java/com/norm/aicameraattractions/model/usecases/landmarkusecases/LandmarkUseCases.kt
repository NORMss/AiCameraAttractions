package com.norm.aicameraattractions.model.usecases.landmarkusecases

data class LandmarkUseCases(
    val upsertLandmark: UpsertLandmark,
    val deleteLandmark: DeleteLandmark,
    val selectLandmarks: SelectLandmarks,
    val selectLandmark: SelectLandmark,
)
