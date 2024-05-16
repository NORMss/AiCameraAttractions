package com.norm.aicameraattractions.model

import androidx.annotation.DrawableRes

data class Attraction(
    @DrawableRes val image: Int,
    val description: String,
    val region: String,
)
