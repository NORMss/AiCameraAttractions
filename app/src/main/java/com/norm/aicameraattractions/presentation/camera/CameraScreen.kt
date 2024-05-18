package com.norm.aicameraattractions.presentation.camera

import android.app.Activity
import android.util.Log
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.norm.aicameraattractions.MainActivity
import com.norm.aicameraattractions.data.LandmarkClassifierImpl
import com.norm.aicameraattractions.data.LandmarkImageAnalyzer
import com.norm.aicameraattractions.model.Classification
import com.norm.aicameraattractions.presentation.extra_large_padding
import com.norm.aicameraattractions.presentation.large_rounded
import com.norm.aicameraattractions.presentation.size_box_camera_button
import com.norm.aicameraattractions.presentation.size_icon_camera_button
import com.norm.aicameraattractions.presentation.smale_padding

@Composable
fun CameraScreen(
    activity: Activity,
    onTakePhoto: (LifecycleCameraController) -> Unit,
    onOpenGallery: () -> Unit,
) {
    var classification by remember {
        mutableStateOf(emptyList<Classification>())
    }
    val analyzer = remember {
        LandmarkImageAnalyzer(
            classifier = LandmarkClassifierImpl(
                context = activity.applicationContext
            ),
            onResults = {
                classification = it
            }
        )
    }
    val controller = remember {
        LifecycleCameraController(activity.applicationContext).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.IMAGE_ANALYSIS
            )
            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(activity.applicationContext),
                analyzer,
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PreviewView(it).apply {
                    this.controller = controller
                    controller.bindToLifecycle(lifecycleOwner)
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Log.d("MyLog", classification.toString())
            classification.forEach {
                Text(
                    text = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(smale_padding),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
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
                        if ((activity as MainActivity).arePermissionGranted()) {
                            onTakePhoto(controller)
                        }
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