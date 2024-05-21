package com.norm.aicameraattractions.presentation.nvgarph

sealed class Route(
    val route: String,
) {
    data object CameraScreen : Route(route = "cameraScreen")
    data object GalleryScreen : Route(route = "galleryScreen")
    data object DetailsScreen : Route(route = "detailsScreen")
    data object Navigation : Route(route = "navigation")
    data object NavigatorScreen : Route(route = "navigatorScreen")
}