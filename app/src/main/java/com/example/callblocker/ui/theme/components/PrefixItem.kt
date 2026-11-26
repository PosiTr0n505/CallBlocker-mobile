package com.example.callblocker.ui.theme.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.callblocker.R
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.callblocker.models.PhoneNormalizer

@Composable
fun PrefixItem(prefix: String, onRemove: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(PhoneNormalizer.toDisplay(prefix), Modifier.weight(1f))
        TextButton(onClick = onRemove) { Text(stringResource(R.string.remove)) }
    }
}