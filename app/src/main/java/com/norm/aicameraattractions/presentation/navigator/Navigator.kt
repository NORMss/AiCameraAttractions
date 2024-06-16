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
import androidx.navigation.toRoute
import com.norm.aicameraattractions.domain.model.Landmark
import com.norm.aicameraattractions.presentation.camera.CameraScreen
import com.norm.aicameraattractions.presentation.camera.CameraViewModel
import com.norm.aicameraattractions.presentation.detail.DetailsScreen
import com.norm.aicameraattractions.presentation.detail.DetailsViewModel
import com.norm.aicameraattractions.presentation.gallery.GalleryScreen
import com.norm.aicameraattractions.presentation.gallery.GalleryViewModel
import com.norm.aicameraattractions.presentation.nvgarph.NewRoute
import com.norm.aicameraattractions.presentation.nvgarph.Route

@Composable
fun Navigator() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NewRoute.GalleryScreen,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        composable<Route.DetailsScreen> { backStackEntry ->
            val viewModel = hiltViewModel<DetailsViewModel>()
            val state = viewModel.state.collectAsState().value
//            navController.previousBackStackEntry?.savedStateHandle?.get<Uri>("image")
//                ?.let { uri ->
//                    viewModel.getLandmark(uri)
//                }
            backStackEntry.toRoute<NewRoute.DetailsScreen>().let { detailsScreen ->
                viewModel.getLandmark(Uri.parse(detailsScreen.uri))
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
        composable<NewRoute.GalleryScreen> {
            val viewModel = hiltViewModel<GalleryViewModel>()
            val state = viewModel.state.value
            GalleryScreen(
                state = state,
                landmarks = state.landmarksList,
                onOpenCamera = {
                    navigateToScreens(
                        navController = navController,
                        route = NewRoute.CameraScreen,
                    )
                },
                onDetailsClick = {
//                    navigateToDetails(
//                        navController = navController,
//                        uri = it
//                    )
                    navController.navigate(
                        NewRoute.DetailsScreen(uri = it.toString())
                    )
                },
                selectFilter = {
                    viewModel.selectFilter(it)
                },
                onSearchChange = {
                    viewModel.onSearchChange(it)
                },
                onChangeSearchText = {
                    viewModel.onChangeSearchText(it)
                }
            )
        }
        composable<NewRoute.CameraScreen> {
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
                        route = NewRoute.GalleryScreen
                    )
                },
                onSelectRegion = viewModel::selectRegion,
                onSetClassification = viewModel::setClassification,
                onStartDownload = viewModel::startDownload,
            )
        }
    }
}

private fun navigateToScreens(
    navController: NavController,
    route: NewRoute,
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