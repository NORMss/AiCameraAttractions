package com.norm.aicameraattractions.presentation.navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.norm.aicameraattractions.MainActivity
import com.norm.aicameraattractions.presentation.camera.CameraScreen
import com.norm.aicameraattractions.presentation.camera.CameraViewModel
import com.norm.aicameraattractions.presentation.gallery.GalleryScreen
import com.norm.aicameraattractions.presentation.nvgarph.Route

@Composable
fun Navigator(activity: MainActivity) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.GalleryScreen.route,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        composable(
            route = Route.GalleryScreen.route,
        ) {
            GalleryScreen(
                attractions = emptyList(),
                onOpenCamera = {
                    navigateToScreens(
                        navController = navController,
                        route = Route.CameraScreen.route,
                    )
                }
            )
        }
        composable(
            route = Route.CameraScreen.route,
        ) {
            val viewModel = hiltViewModel<CameraViewModel>()
            CameraScreen(
                activity = activity,
                onTakePhoto = {
                    viewModel.onTakePhoto(it)
                },
                onOpenGallery = {
                    navigateToScreens(
                        navController = navController,
                        route = Route.GalleryScreen.route,
                    )
                }
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