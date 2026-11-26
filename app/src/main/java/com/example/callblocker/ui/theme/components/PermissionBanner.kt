package com.example.callblocker.ui.theme.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.callblocker.R

@Composable
fun PermissionBanner(onGrant: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onGrant, modifier = modifier) { Text(stringResource(R.string.grant_permission)) }
}