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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class InventoryRepository(
    private val inventoryLocalDataSource: IInventoryLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : IInventoryRepository {
  override suspend fun getAllActiveWithStock(): List<StockItem> =
      withContext(ioDispatcher) { inventoryLocalDataSource.getAllActiveWithStock() }

  override suspend fun updateStockAndPrice(
      productId: Long,
      currentQty: Int,
      price: Double,
  ): Unit =
      withContext(ioDispatcher) {
        inventoryLocalDataSource.updateStockAndPrice(productId, currentQty, price)
      }
}
