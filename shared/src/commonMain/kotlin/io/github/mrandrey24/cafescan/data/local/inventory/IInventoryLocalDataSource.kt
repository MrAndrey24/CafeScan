package io.github.mrandrey24.cafescan.data.local.inventory

import io.github.mrandrey24.cafescan.domain.entities.StockItem

interface IInventoryLocalDataSource {
    suspend fun getAllActiveWithStock(): List<StockItem>
}
