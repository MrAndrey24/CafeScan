package io.github.mrandrey24.cafescan.data.repository.inventory

import io.github.mrandrey24.cafescan.data.local.inventory.IInventoryLocalDataSource
import io.github.mrandrey24.cafescan.domain.entities.StockItem

class InventoryRepository(
    private val inventoryLocalDataSource: IInventoryLocalDataSource
) : IInventoryRepository {
    override suspend fun getAllActiveWithStock(): List<StockItem> {
        return inventoryLocalDataSource.getAllActiveWithStock()
    }
}
