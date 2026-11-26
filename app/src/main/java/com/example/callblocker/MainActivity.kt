package com.example.callblocker

import android.Manifest
import android.app.role.RoleManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.callblocker.models.PrefixRepository
import com.example.callblocker.screens.MainScreen
import com.example.callblocker.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val roleManager by lazy { getSystemService(RoleManager::class.java) }
    private var hasScreeningRole by mutableStateOf(false)

    private val roleLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { refreshRole() }

    private val notificationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        setContent {
            MaterialTheme {
                val state by viewModel.state.collectAsState()
                MainScreen(
                    state = state,
                    hasScreeningRole = hasScreeningRole,
                    onRequestRole = ::requestRole,
                    onToggle = viewModel::onToggle,
                    onInputChange = viewModel::onInputChange,
                    onAdd = viewModel::onAdd,
                    onRemove = viewModel::onRemove,
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshRole()
    }

    private fun refreshRole() {
        hasScreeningRole = roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)
    }

    private fun requestRole() {
        roleLauncher.launch(roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING))
    }

}

