package io.github.mrandrey24.cafescan.presentation.state

import androidx.compose.ui.graphics.ImageBitmap
import io.github.mrandrey24.cafescan.domain.entities.Product

data class QrPreviewUiState(
    val product: Product? = null,
    val isLoading: Boolean = true,
    val qrBitmap: ImageBitmap? = null,
    val isSharing: Boolean = false,
    val errorMessage: String? = null
)
