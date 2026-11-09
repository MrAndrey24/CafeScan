/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.presentation.state

import androidx.compose.ui.graphics.ImageBitmap
import io.github.mrandrey24.cafescan.domain.entities.Product

data class QrPreviewUiState(
    val product: Product? = null,
    val isLoading: Boolean = true,
    val qrBitmap: ImageBitmap? = null,
    val isSharing: Boolean = false,
    val errorMessage: String? = null,
)
