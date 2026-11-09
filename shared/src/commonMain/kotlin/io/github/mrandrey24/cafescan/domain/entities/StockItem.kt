/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.domain.entities

data class StockItem(
    val product: Product,
    val currentQty: Int,
    val initialQty: Int,
    val lowStockThreshold: Int,
) {
  val status: StockStatus
    get() =
        when {
          currentQty <= 0 -> StockStatus.OUT_OF_STOCK
          currentQty <= lowStockThreshold -> StockStatus.CRITICAL
          currentQty <= lowStockThreshold * 2 -> StockStatus.LOW
          else -> StockStatus.HEALTHY
        }
}
