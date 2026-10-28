package io.github.mrandrey24.cafescan.data.local.inventory

import io.github.mrandrey24.cafescan.data.local.AppDatabase
import io.github.mrandrey24.cafescan.domain.entities.Product
import io.github.mrandrey24.cafescan.domain.entities.StockItem

class InventoryLocalDataSource(database: AppDatabase) : IInventoryLocalDataSource {
    private val dbQuery = database.appDatabaseQueries

    override suspend fun getAllActiveWithStock(): List<StockItem> {
        return dbQuery.getAllActiveWithStock { id, name, price, cost, emoji, qrString, isActive, currentQty, initialQty, lowStockThreshold, stockUpdatedAt ->
            val product = Product(
                id = id,
                name = name,
                price = price,
                cost = cost,
                emoji = emoji,
                qrString = qrString,
                isActive = isActive == 1L
            )
            StockItem(
                product = product,
                currentQty = (currentQty ?: 0L).toInt(),
                initialQty = (initialQty ?: 0L).toInt(),
                lowStockThreshold = (lowStockThreshold ?: 3L).toInt()
            )
        }.executeAsList()
    }
}
