package com.norm.aicameraattractions

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.norm.aicameraattractions.presentation.nvgarph.NavGraph
import com.norm.aicameraattractions.presentation.nvgarph.Route
import com.norm.aicameraattractions.ui.theme.AiCameraAttractionsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT,
            )
        )

        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED,
                )
            } else {
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                )
            }
        } else {
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
            )
        }

        super.onCreate(savedInstanceState)
        setContent {
            AiCameraAttractionsTheme {
                val showDialog =
                    mainViewModel.showDialog.collectAsState().value
                val launchAppSettings =
                    mainViewModel.launchAppSettings.collectAsState().value

                val permissionResultActivityLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { result ->
                        permissions.forEach { permission ->
                            if (result[permission] == false) {
                                if (!shouldShowRequestPermissionRationale(permission)) {
                                    mainViewModel.updateLaunchAppSettings(true)
                                }
                                mainViewModel.updateShowDialog(true)
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = Unit) {
                    permissions.forEach { permission ->
                        val isGranted = checkSelfPermission(permission) ==
                                PackageManager.PERMISSION_GRANTED
                        if (!isGranted) {
                            if (shouldShowRequestPermissionRationale(permission)) {
                                mainViewModel.updateShowDialog(true)
                            } else {
                                permissionResultActivityLauncher.launch(permissions)
                            }
                        }
                    }
                }

                if (showDialog) {
                    PermissionDialog(
                        onDismiss = {
                            mainViewModel.updateShowDialog(false)
                        },
                        onConfirm = {
                            mainViewModel.updateShowDialog(true)

                            if (launchAppSettings) {
                                Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", packageName, null)
                                ).also {
                                    startActivity(it)
                                }
                                mainViewModel.updateLaunchAppSettings(false)
                            } else {
                                permissionResultActivityLauncher.launch(permissions)
                            }
                        }
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(
                        startDestination = Route.Navigation.route,
                    )
                }
            }
        }
    }
}

@Composable
fun PermissionDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth(),
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(
                    text = "OK"
                )
            }
        },
        title = {
            Text(
                text = "Camera and Media permissions are needed",
                fontWeight = FontWeight.SemiBold,
            )
        },
        text = {
            Text(
                text = "This application requires access to the camera and gallery"
            )
        }
    )
}