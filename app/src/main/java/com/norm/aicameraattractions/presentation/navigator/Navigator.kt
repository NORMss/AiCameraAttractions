package com.norm.aicameraattractions.presentation.navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.norm.aicameraattractions.presentation.camera.CameraScreen
import com.norm.aicameraattractions.presentation.gallery.GalleryScreen
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
            route = Route.GalleryScreen.route,
        ) {
            GalleryScreen(
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
            CameraScreen(
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