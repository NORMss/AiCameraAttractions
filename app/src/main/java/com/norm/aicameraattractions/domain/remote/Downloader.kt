package com.norm.aicameraattractions.domain.remote

interface Downloader {
    fun downloadFile(url: String): Long
}