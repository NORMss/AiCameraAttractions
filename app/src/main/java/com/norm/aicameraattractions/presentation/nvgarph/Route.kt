package com.norm.aicameraattractions.presentation.nvgarph

import kotlinx.serialization.Serializable

sealed class Route(
    val route: String,
) {
    data object CameraScreen : Route(route = "cameraScreen")
    data object GalleryScreen : Route(route = "galleryScreen")
    data object DetailsScreen : Route(route = "detailsScreen")
    data object Navigation : Route(route = "navigation")
    data object NavigatorScreen : Route(route = "navigatorScreen")
}
@Serializable
sealed class NewRoute{
    @Serializable
    data object CameraScreen : NewRoute()
    @Serializable
    data object GalleryScreen : NewRoute()
    @Serializable
    data class DetailsScreen(
        val uri: String,
    ) : NewRoute()
    @Serializable
    data object Navigation : NewRoute()
    @Serializable
    data object NavigatorScreen : NewRoute()
}