package com.norm.aicameraattractions.presentation.navigator

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.norm.aicameraattractions.model.Landmark
import com.norm.aicameraattractions.presentation.camera.CameraScreen
import com.norm.aicameraattractions.presentation.camera.CameraViewModel
import com.norm.aicameraattractions.presentation.detail.DetailsScreen
import com.norm.aicameraattractions.presentation.detail.DetailsViewModel
import com.norm.aicameraattractions.presentation.gallery.GalleryScreen
import com.norm.aicameraattractions.presentation.gallery.GalleryViewModel
import com.norm.aicameraattractions.presentation.nvgarph.Route

@Composable
fun Navigator() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.GalleryScreen.route,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        composable(
            route = Route.DetailsScreen.route,
        ) {
            val viewModel = hiltViewModel<DetailsViewModel>()
            val state = viewModel.state.collectAsState().value
            navController.previousBackStackEntry?.savedStateHandle?.get<Uri>("image")
                ?.let { uri ->
                    viewModel.getLandmark(uri)
                }
            DetailsScreen(
                landmark = state.selectLandmark ?: Landmark(Uri.parse(""), "", ""),
                onBackClick = {
                    navController.navigateUp()
                },
                onDeleteClick = {
                    viewModel.deleteLandmark()
                    navController.navigateUp()
                },
                onShareClick = {

                }
            )
        }
        composable(
            route = Route.GalleryScreen.route,
        ) {
            val viewModel = hiltViewModel<GalleryViewModel>()
            val state = viewModel.state.value
            GalleryScreen(
                landmarks = state.landmarksList,
                onOpenCamera = {
                    navigateToScreens(
                        navController = navController,
                        route = Route.CameraScreen.route,
                    )
                },
                onDetailsClick = {
                    navigateToDetails(
                        navController = navController,
                        uri = it
                    )
                }
            )
        }
        composable(
            route = Route.CameraScreen.route,
        ) {
            val viewModel = hiltViewModel<CameraViewModel>()
            val state = viewModel.state.value
            CameraScreen(
                state = state,
                onTakePhoto = { controller, landmark, region ->
                    viewModel.onTakePhoto(
                        controller,
                        landmark.name,
                        region.name,
                    )
                },
                onCameraSelector = {
                    viewModel.onCameraSelector(it)
                },
                onOpenGallery = {
                    navigateToScreens(
                        navController = navController,
                        route = Route.GalleryScreen.route,
                    )
                },
                selectRegion = viewModel::selectRegion
            )
        }
    }
}

private fun navigateToScreens(
    navController: NavController,
    route: String,
) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { galleryScreen ->
            popUpTo(galleryScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(
    navController: NavController,
    uri: Uri,
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("image", uri)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}