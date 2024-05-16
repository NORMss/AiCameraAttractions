package com.norm.aicameraattractions

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.norm.aicameraattractions.presentation.nvgarph.NavGraph
import com.norm.aicameraattractions.presentation.nvgarph.Route
import com.norm.aicameraattractions.ui.theme.AiCameraAttractionsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (!arePermissionGranted()) {
            ActivityCompat.requestPermissions(
                this, CAMERA_PERMISSIONS, 0
            )
        }
        super.onCreate(savedInstanceState)
        setContent {
            AiCameraAttractionsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(
                        mainActivity = this,
                        startDestination = Route.Navigation.route,
                    )
                }
            }
        }
    }

    fun arePermissionGranted(): Boolean {
        return CAMERA_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                applicationContext,
                permission,
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        val CAMERA_PERMISSIONS = arrayOf(
            android.Manifest.permission.CAMERA,
        )
    }
}