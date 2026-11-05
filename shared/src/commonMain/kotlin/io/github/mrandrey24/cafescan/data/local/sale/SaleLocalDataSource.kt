/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.data.local.sale

import io.github.mrandrey24.cafescan.data.local.AppDatabase
import io.github.mrandrey24.cafescan.domain.entities.OrderItem
import io.github.mrandrey24.cafescan.domain.entities.Product
import io.github.mrandrey24.cafescan.domain.entities.Sale
import io.github.mrandrey24.cafescan.domain.entities.TopProduct
import kotlin.time.Clock

class SaleLocalDataSource(database: AppDatabase) : ISaleLocalDataSource {
  private val dbQuery = database.appDatabaseQueries

  override suspend fun saveSale(sale: Sale): Sale {
    return dbQuery.transactionWithResult {
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

  override suspend fun getSalesForDateRange(startMs: Long, endMs: Long): List<Sale> {
    val sales = dbQuery.getSalesByDateRange(startMs, endMs).executeAsList()
    return sales.map { saleRow ->
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

  override suspend fun getTopProducts(startMs: Long, endMs: Long): List<TopProduct> {
    val rows = dbQuery.getTopProductsByDateRange(startMs, endMs).executeAsList()
    return rows.map { row ->
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
