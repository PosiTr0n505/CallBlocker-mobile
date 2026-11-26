package com.example.callblocker.ui.theme.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PrefixList(
    prefixes: List<String>,
    onRemove: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(prefixes, key = { it }) { prefix ->
            PrefixItem(prefix = prefix, onRemove = { onRemove(prefix) })
        }
    }
}