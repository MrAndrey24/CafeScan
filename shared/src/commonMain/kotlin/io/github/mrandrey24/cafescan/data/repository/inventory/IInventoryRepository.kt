package io.github.mrandrey24.cafescan.data.repository.inventory

import io.github.mrandrey24.cafescan.domain.entities.StockItem

interface IInventoryRepository {
    suspend fun getAllActiveWithStock(): List<StockItem>
}
