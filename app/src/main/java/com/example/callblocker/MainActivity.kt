package com.example.callblocker

import android.Manifest
import android.app.role.RoleManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.callblocker.models.PrefixStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { Screen() } }
    }

    @Composable
    fun Screen() {
        val store = remember { PrefixStore(this) }
        var enabled by remember { mutableStateOf(store.enabled) }
        var prefixes by remember { mutableStateOf(store.prefixes().sorted()) }
        var input by remember { mutableStateOf("") }
        var error by remember { mutableStateOf(false) }

        val roleManager = remember { getSystemService(RoleManager::class.java) }
        var hasRole by remember { mutableStateOf(roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) }

        val roleLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { hasRole = roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING) }

        val notifLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { }

        LaunchedEffect(Unit) { notifLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) }

        Column(Modifier.padding(16.dp).statusBarsPadding(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("CallBlocker", style = MaterialTheme.typography.headlineMedium)

            if (!hasRole) {
                Button(onClick = {
                    roleLauncher.launch(roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING))
                }) { Text("Grant call screening permission") }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Blocking active", Modifier.weight(1f))
                Switch(checked = enabled, onCheckedChange = { enabled = it; store.enabled = it })
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it; error = false },
                    label = { Text("Prefix (e.g. 04 24)") },
                    isError = error,
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = {
                    if (store.add(input)) { input = ""; prefixes = store.prefixes().sorted() } else error = true
                }) { Text("Add") }
            }
            if (error) Text("At least 3 digits please", color = MaterialTheme.colorScheme.error)

            LazyColumn {
                items(prefixes) { p ->
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("0$p…", Modifier.weight(1f))
                        TextButton(onClick = { store.remove(p); prefixes = store.prefixes().sorted() }) {
                            Text("Remove")
                        }
                    }
                }
            }
        }
    }
}

