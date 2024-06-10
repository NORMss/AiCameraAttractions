package com.norm.aicameraattractions.domain.model

data class Region(
    val name: String,
    val tfModel: String,
    val downloadState: DownloadState,
)

sealed class DownloadState(val message: String? = null) {
    class Downloaded(message: String) : DownloadState(message)

    class Downloading(message: String) : DownloadState(message)
    class NotDownloaded(message: String) : DownloadState(message)
    class Error(message: String) : DownloadState(message)
}
