/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.presentation.state

import io.github.mrandrey24.cafescan.domain.entities.StockItem

data class InventoryUiState(
    val items: List<StockItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
