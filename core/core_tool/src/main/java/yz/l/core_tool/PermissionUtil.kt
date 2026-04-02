package yz.l.core_tool

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun requestPermission() {
    val permissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    var showPermissionRationaleDialog by remember { mutableStateOf(false) }
    var showGoToSettingsDialog by remember { mutableStateOf(false) }
    // 记录权限是否被拒绝过
    var permissionDeniedBefore by remember { mutableStateOf(false) }
}