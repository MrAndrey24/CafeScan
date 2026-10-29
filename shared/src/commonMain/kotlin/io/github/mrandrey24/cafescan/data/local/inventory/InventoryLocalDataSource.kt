package io.github.mrandrey24.cafescan.data.local.inventory

import io.github.mrandrey24.cafescan.data.local.AppDatabase
import io.github.mrandrey24.cafescan.domain.entities.Product
import io.github.mrandrey24.cafescan.domain.entities.QrPayload
import io.github.mrandrey24.cafescan.domain.entities.StockItem
import kotlin.time.Clock

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

    override suspend fun updateStockAndPrice(productId: Long, currentQty: Int, price: Double) {
        dbQuery.transaction {
            val now = Clock.System.now().toEpochMilliseconds()
            dbQuery.insertOrIgnoreStock(
                product_id = productId,
                current_qty = currentQty.toLong(),
                initial_qty = currentQty.toLong(),
                low_stock_threshold = 3L,
                updated_at = now
            )
            dbQuery.updateStockQty(
                currentQty = currentQty.toLong(),
                updatedAt = now,
                productId = productId
            )
            val product = dbQuery.selectProductById(productId, ::mapProductSelecting).executeAsOneOrNull()
            if (product != null) {
                val qrPayload = QrPayload(
                    id = productId.toString(),
                    name = product.name,
                    price = price.toString(),
                    cost = product.cost.toString()
                )
                val qr = qrPayload.toQrString()
                dbQuery.updateProductPriceAndQr(
                    price = price,
                    qrString = qr,
                    updatedAt = now,
                    id = productId
                )
            }
        }
    }

    private fun mapProductSelecting(
        id: Long,
        name: String,
        price: Double,
        cost: Double,
        emoji: String,
        isActive: Long,
        qrString: String,
        createdAt: Long,
        updatedAt: Long
    ): Product {
        return Product(
            id = id,
            name = name,
            price = price,
            cost = cost,
            emoji = emoji,
            qrString = qrString,
            isActive = isActive == 1L,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
