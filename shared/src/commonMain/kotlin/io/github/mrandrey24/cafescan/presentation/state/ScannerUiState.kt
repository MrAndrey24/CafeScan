/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.presentation.state

import io.github.mrandrey24.cafescan.domain.entities.OrderItem
import io.github.mrandrey24.cafescan.domain.entities.Product

data class ScannerUiState(
    val basket: List<OrderItem> = emptyList(),
    val scannedProduct: Product? = null,
    val scanError: ScanError? = null,
    val isScanning: Boolean = true,
    val errorMessage: String? = null,
) {
  val totalAmount: Double
    get() = basket.sumOf { it.subtotal }

  val totalItems: Int
    get() = basket.sumOf { it.quantity }
}
