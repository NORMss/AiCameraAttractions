package com.norm.aicameraattractions.data.local

import android.net.Uri
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class UriTypeConverter {
    @TypeConverter
    fun uriToString(uri: Uri): String {
        return uri.toString()
    }

    @TypeConverter
    fun stringToUri(imagePath: String): Uri? {
        return Uri.parse(imagePath)
    }
}