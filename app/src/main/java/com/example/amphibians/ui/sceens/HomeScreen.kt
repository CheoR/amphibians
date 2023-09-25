package com.example.amphibians.ui.sceens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    amphibianUiState: AmphbianUIState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "moo cow oink"
    )
}