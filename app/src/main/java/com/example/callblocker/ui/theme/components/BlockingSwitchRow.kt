package com.example.callblocker.ui.theme.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.callblocker.R

@Composable
fun BlockingSwitchRow(isEnabled: Boolean, onToggle: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(R.string.blocking_active), Modifier.weight(1f))
        Switch(checked = isEnabled, onCheckedChange = onToggle)
    }
}