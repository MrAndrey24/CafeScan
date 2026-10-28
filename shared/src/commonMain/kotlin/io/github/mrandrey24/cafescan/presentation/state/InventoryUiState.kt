package io.github.mrandrey24.cafescan.presentation.state

import io.github.mrandrey24.cafescan.domain.entities.StockItem

data class InventoryUiState(
    val items: List<StockItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
