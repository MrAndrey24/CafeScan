/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.data.repository.inventory

import io.github.mrandrey24.cafescan.data.local.inventory.IInventoryLocalDataSource
import io.github.mrandrey24.cafescan.domain.entities.StockItem

class InventoryRepository(private val inventoryLocalDataSource: IInventoryLocalDataSource) :
    IInventoryRepository {
  override suspend fun getAllActiveWithStock(): List<StockItem> {
    return inventoryLocalDataSource.getAllActiveWithStock()
  }

  override suspend fun updateStockAndPrice(productId: Long, currentQty: Int, price: Double) {
    inventoryLocalDataSource.updateStockAndPrice(productId, currentQty, price)
  }
}
