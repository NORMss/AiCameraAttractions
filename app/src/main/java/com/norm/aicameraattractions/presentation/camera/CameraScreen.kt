package com.norm.aicameraattractions.presentation.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.norm.aicameraattractions.presentation.extra_large_padding
import com.norm.aicameraattractions.presentation.large_rounded
import com.norm.aicameraattractions.presentation.size_box_camera_button
import com.norm.aicameraattractions.presentation.size_icon_camera_button
import com.norm.aicameraattractions.presentation.smale_padding

@Composable
fun CameraScreen(
    onOpenGallery: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = extra_large_padding)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(large_rounded))
                    .size(size_box_camera_button)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        onOpenGallery()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.PhotoLibrary,
                    contentDescription = "Open Gallery",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(size_icon_camera_button)
                )
            }

            Spacer(modifier = Modifier.width(smale_padding))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(large_rounded))
                    .size(size_box_camera_button)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
//                        if ((activity as MainActivity).arePermissionGranted()) {
//                            cameraViewModel.onTakePhoto(controller)
//                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Camera,
                    contentDescription = "Take Photo",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(size_icon_camera_button)
                )
            }

            Spacer(modifier = Modifier.width(smale_padding))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(large_rounded))
                    .size(size_box_camera_button)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
//                        controller.cameraSelector =
//                            if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
//                                CameraSelector.DEFAULT_FRONT_CAMERA
//                            } else {
//                                CameraSelector.DEFAULT_BACK_CAMERA
//                            }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Cameraswitch,
                    contentDescription = "Switch Camera preview",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(size_icon_camera_button)
                )
            }
        }
    }
}