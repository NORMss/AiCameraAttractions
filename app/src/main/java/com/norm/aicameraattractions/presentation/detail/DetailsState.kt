package com.norm.aicameraattractions.presentation.detail

import com.norm.aicameraattractions.domain.model.Landmark
import com.norm.aicameraattractions.domain.model.Message

data class DetailsState(
    val selectLandmark: Landmark? = null,
    val messages: List<Message> = emptyList(),
    val isSending: Boolean? = null,
)
