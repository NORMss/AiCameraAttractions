package com.norm.aicameraattractions.presentation.camera

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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.norm.aicameraattractions.data.LandmarkClassifierImpl
import com.norm.aicameraattractions.data.LandmarkImageAnalyzer
import com.norm.aicameraattractions.domain.model.Classification
import com.norm.aicameraattractions.domain.model.Region
import com.norm.aicameraattractions.presentation.camera.components.LandmarkNameCard
import com.norm.aicameraattractions.presentation.camera.components.RegionSelectorFlowRow
import com.norm.aicameraattractions.presentation.extra_large_padding
import com.norm.aicameraattractions.presentation.large_rounded
import com.norm.aicameraattractions.presentation.medium_padding
import com.norm.aicameraattractions.presentation.size_box_camera_button
import com.norm.aicameraattractions.presentation.size_icon_camera_button
import com.norm.aicameraattractions.presentation.smale_padding

@Composable
fun CameraScreen(
    state: CameraState,
    onTakePhoto: (LifecycleCameraController, Classification, Region) -> Unit,
    onCameraSelector: (LifecycleCameraController) -> Unit,
    onOpenGallery: () -> Unit,
    onSelectRegion: (Region) -> Unit,
    onSetClassification: (List<Classification>) -> Unit,
    onStartDownload: (Region) -> Unit,
) {
    val localContext = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val analyzer = remember(state.currentRegion) {
        LandmarkImageAnalyzer(
            classifier = LandmarkClassifierImpl(
                context = localContext
            ),
            onResults = {
                onSetClassification(it)
            },
            modelPath = state.currentRegion!!.tfModel,
        )
    }

    val controller = remember(state.currentRegion) {
        LifecycleCameraController(localContext).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.IMAGE_ANALYSIS
            )
            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(localContext),
                analyzer,
            )
            bindToLifecycle(lifecycleOwner)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PreviewView(it).apply {
                    this.controller = controller
                }
            },
            update = {
                it.controller = controller
            }
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = extra_large_padding)
        ) {
//            RegionSelector(
//                regions = regions,
//                selectedRegion = state.currentRegion,
//                onRegionSelect = {
//                    selectRegion(it)
//                }
//            )
            RegionSelectorFlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = medium_padding),
                regions = state.regions,
                selectedRegion = state.currentRegion!!,
                onRegionSelect = {
                    onSelectRegion(it)
                },
                onStartDownload = {
                    onStartDownload(it)
                },
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            state.classification.forEach {
                LandmarkNameCard(
                    classification = Classification(
                        name = it.name,
                        score = it.score,
                    )
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
                        if (state.classification.isNotEmpty()) {
                            onTakePhoto(
                                controller,
                                state.classification[0],
                                state.currentRegion!!,
                            )
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
                        onCameraSelector(controller)
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