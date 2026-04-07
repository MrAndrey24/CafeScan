package io.github.mrandrey24.cafescan.presentation.state

import io.github.mrandrey24.cafescan.domain.entities.OrderItem

data class ScannerUiState(
    val basket: List<OrderItem> = emptyList(),
) {
  val totalAmount: Double
    get() = basket.sumOf { it.subtotal }

  val totalItems: Int
    get() = basket.sumOf { it.quantity }
}
