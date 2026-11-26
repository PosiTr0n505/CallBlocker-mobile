package com.example.callblocker.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.callblocker.R
import com.example.callblocker.ui.theme.MainUiState
import com.example.callblocker.ui.theme.Spacing
import com.example.callblocker.ui.theme.components.BlockingSwitchRow
import com.example.callblocker.ui.theme.components.PermissionBanner
import com.example.callblocker.ui.theme.components.PrefixInputRow
import com.example.callblocker.ui.theme.components.PrefixList

@Composable
fun MainScreen(
    state: MainUiState,
    hasScreeningRole: Boolean,
    onRequestRole: () -> Unit,
    onToggle: (Boolean) -> Unit,
    onInputChange: (String) -> Unit,
    onAdd: () -> Unit,
    onRemove: (String) -> Unit,
) {
    Column(
        Modifier.statusBarsPadding().padding(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        Text(stringResource(R.string.app_name), style = MaterialTheme.typography.headlineMedium)

        if (!hasScreeningRole) PermissionBanner(onGrant = onRequestRole)

        BlockingSwitchRow(isEnabled = state.isEnabled, onToggle = onToggle)

        PrefixInputRow(
            value = state.input,
            isError = state.isInputError,
            onValueChange = onInputChange,
            onAdd = onAdd,
        )

        PrefixList(prefixes = state.prefixes, onRemove = onRemove)
    }
}