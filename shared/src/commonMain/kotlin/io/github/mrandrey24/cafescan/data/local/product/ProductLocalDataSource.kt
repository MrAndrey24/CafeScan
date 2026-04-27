package io.github.mrandrey24.cafescan.data.local.product

import io.github.mrandrey24.cafescan.data.local.AppDatabase
import io.github.mrandrey24.cafescan.domain.entities.Product
import io.github.mrandrey24.cafescan.domain.entities.QrPayload
import kotlin.time.Clock

class ProductLocalDataSource(database: AppDatabase) : IProductLocalDataSource {
    private val dbQuery = database.appDatabaseQueries


    override fun createProduct(product: Product): Long {
        return dbQuery.transactionWithResult {
            val now = Clock.System.now().toEpochMilliseconds()

            dbQuery.insertProduct(
                id = if (product.id == 0L) null else product.id,
                name = product.name,
                price = product.price,
                cost = product.cost,
                emoji = product.emoji ?: "",
                qr_string = toString(),
                is_active = if (product.isActive) 1L else 0L,
                created_at = now,
                updated_at = now
            )

            val productId = dbQuery.lastInsertRowId().executeAsOne()

            dbQuery.insertOrIgnoreStock(
                product_id = productId,
                current_qty = product.stock,
                initial_qty = product.stock,
                low_stock_threshold = 3L,
                updated_at = now
            )

            val qrPayload = QrPayload(
                id = productId.toString(),
                name = product.name,
                price = product.price.toString(),
                cost = product.cost.toString()

            )

            val qr = qrPayload.toString()

            dbQuery.updateProduct(
                id = productId,
                name = product.name,
                price = product.price,
                cost = product.cost,
                emoji = product.emoji ?: "",
                isActive = 1L,
                updatedAt = now,
                qrString = qr
            )

            productId
        }
    }

    override suspend fun getAllProducts(): List<Product> {
        return dbQuery.selectAllProducts(::mapProductSelecting).executeAsList()
    }

    override suspend fun getProductById(id: Long): Product? {
        return dbQuery.selectProductById(id, ::mapProductSelecting).executeAsOneOrNull()
    }

    override suspend fun getProductByName(name: String): Boolean {
        return dbQuery.findProductByName(name).executeAsOne()
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
