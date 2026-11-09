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
package io.github.mrandrey24.cafescan.data.local.sale

import io.github.mrandrey24.cafescan.data.local.AppDatabase
import io.github.mrandrey24.cafescan.domain.entities.OrderItem
import io.github.mrandrey24.cafescan.domain.entities.Product
import io.github.mrandrey24.cafescan.domain.entities.Sale
import io.github.mrandrey24.cafescan.domain.entities.TopProduct
import kotlin.time.Clock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class SaleLocalDataSource(
    database: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ISaleLocalDataSource {
  private val dbQuery = database.appDatabaseQueries

  override suspend fun saveSale(sale: Sale): Sale =
      withContext(ioDispatcher) {
        dbQuery.transactionWithResult {
          val now = Clock.System.now().toEpochMilliseconds()
          val saleId = if (sale.id.isNotBlank()) sale.id.toLongOrNull() ?: now else now

          dbQuery.insertSale(
              id = saleId,
              timestamp = if (sale.timestamp == 0L) now else sale.timestamp,
              total = sale.total,
              profit = sale.profit,
          )

          sale.items.forEach { item ->
            dbQuery.insertSaleItem(
                saleId = saleId,
                productId = item.product.id,
                quantity = item.quantity.toLong(),
                unitPrice = item.product.price,
                unitCost = item.product.cost,
                subtotal = item.subtotal,
            )
          }

          sale.copy(
              id = saleId.toString(),
              timestamp = if (sale.timestamp == 0L) now else sale.timestamp,
          )
        }
      }

  override suspend fun getSalesForDateRange(startMs: Long, endMs: Long): List<Sale> =
      withContext(ioDispatcher) {
        val sales = dbQuery.getSalesByDateRange(startMs, endMs).executeAsList()
        sales.map { saleRow ->
          val itemsRows = dbQuery.getSaleItemsBySaleId(saleRow.id).executeAsList()
          val orderItems =
              itemsRows.map { itemRow ->
                OrderItem(
                    product =
                        Product(
                            id = itemRow.product_id,
                            name = itemRow.product_name,
                            price = itemRow.unit_price,
                            cost = itemRow.unit_cost,
                            emoji = itemRow.product_emoji,
                            qrString = itemRow.product_qr_string,
                            isActive = itemRow.product_is_active == 1L,
                        ),
                    quantity = itemRow.quantity.toInt(),
                )
              }
          Sale(
              id = saleRow.id.toString(),
              items = orderItems,
              timestamp = saleRow.timestamp,
          )
        }
      }

  override suspend fun getTopProducts(startMs: Long, endMs: Long): List<TopProduct> =
      withContext(ioDispatcher) {
        val rows = dbQuery.getTopProductsByDateRange(startMs, endMs).executeAsList()
        rows.map { row ->
          TopProduct(
              id = row.id,
              name = row.name,
              price = row.price,
              emoji = row.emoji,
              totalSold = row.total_sold ?: 0L,
              totalRevenue = row.total_revenue ?: 0.0,
          )
        }
      }
}
