package com.norm.aicameraattractions.presentation.gallery.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.norm.aicameraattractions.presentation.smale_padding

@Composable
fun EmptyAttractionList(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Default.NoPhotography,
                contentDescription = "NoPhotography",
            )
            Spacer(
                modifier = Modifier.height(smale_padding),
            )
            Text(
                text = "Gallery empty\nTake first photo",
                textAlign = TextAlign.Center,
            )
        }
    }
}