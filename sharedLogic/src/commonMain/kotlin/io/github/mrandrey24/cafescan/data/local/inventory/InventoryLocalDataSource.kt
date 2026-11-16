/**
 * MIT License
 *
 * Copyright (c) 2026 Andrey Acosta  Ramirez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.mrandrey24.cafescan.data.local.inventory

import io.github.mrandrey24.cafescan.data.local.AppDatabase
import io.github.mrandrey24.cafescan.domain.entities.Product
import io.github.mrandrey24.cafescan.domain.entities.QrPayload
import io.github.mrandrey24.cafescan.domain.entities.StockItem
import kotlin.time.Clock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class InventoryLocalDataSource(
    database: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : IInventoryLocalDataSource {
  private val dbQuery = database.appDatabaseQueries

  override suspend fun getAllActiveWithStock(): List<StockItem> =
      withContext(ioDispatcher) {
        dbQuery
            .getAllActiveWithStock {
                id,
                name,
                price,
                cost,
                emoji,
                qrString,
                isActive,
                currentQty,
                initialQty,
                lowStockThreshold,
                stockUpdatedAt ->
              val product =
                  Product(
                      id = id,
                      name = name,
                      price = price,
                      cost = cost,
                      emoji = emoji,
                      qrString = qrString,
                      isActive = isActive == 1L,
                  )
              StockItem(
                  product = product,
                  currentQty = (currentQty ?: 0L).toInt(),
                  initialQty = (initialQty ?: 0L).toInt(),
                  lowStockThreshold = (lowStockThreshold ?: 3L).toInt(),
              )
            }
            .executeAsList()
      }

  override suspend fun updateStockAndPrice(
      productId: Long,
      currentQty: Int,
      price: Double,
  ): Unit =
      withContext(ioDispatcher) {
        dbQuery.transaction {
          val now = Clock.System.now().toEpochMilliseconds()
          dbQuery.insertOrIgnoreStock(
              product_id = productId,
              current_qty = currentQty.toLong(),
              initial_qty = currentQty.toLong(),
              low_stock_threshold = 3L,
              updated_at = now,
          )
          dbQuery.updateStockQty(
              currentQty = currentQty.toLong(),
              updatedAt = now,
              productId = productId,
          )
          val product =
              dbQuery.selectProductById(productId, ::mapProductSelecting).executeAsOneOrNull()
          if (product != null) {
            val qrPayload =
                QrPayload(
                    id = productId.toString(),
                    name = product.name,
                    price = price.toString(),
                    cost = product.cost.toString(),
                )
            val qr = qrPayload.toQrString()
            dbQuery.updateProductPriceAndQr(
                price = price,
                qrString = qr,
                updatedAt = now,
                id = productId,
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
      updatedAt: Long,
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
        updatedAt = updatedAt,
    )
  }
}
