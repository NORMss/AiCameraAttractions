package com.norm.aicameraattractions.ui.theme

import androidx.compose.ui.graphics.Color
import com.norm.aicameraattractions.ui.theme.Accuracy.*

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

fun colorForAccuracy(accuracy: Accuracy) =
    when (accuracy) {
        VERY_LOW -> Color(0xFFFF5722)

        LOW -> Color(0xFFFFEB3B)

        MEDIUM -> Color(0xFFCDDC39)

        HIGH -> Color(0xFF4CAF50)

    }

enum class Accuracy {
    VERY_LOW, LOW, MEDIUM, HIGH
}