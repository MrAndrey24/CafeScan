/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.data.local.product

import io.github.mrandrey24.cafescan.data.local.AppDatabase
import io.github.mrandrey24.cafescan.domain.entities.Product
import io.github.mrandrey24.cafescan.domain.entities.QrPayload
import kotlin.time.Clock

class ProductLocalDataSource(database: AppDatabase) : IProductLocalDataSource {
  private val dbQuery = database.appDatabaseQueries

  override fun createProduct(product: Product): Product {
    return dbQuery.transactionWithResult {
      val now = Clock.System.now().toEpochMilliseconds()

      dbQuery.insertProduct(
          id = if (product.id == 0L) null else product.id,
          name = product.name,
          price = product.price,
          cost = product.cost,
          emoji = product.emoji ?: "",
          qr_string = product.qrString ?: "",
          is_active = if (product.isActive) 1L else 0L,
          created_at = now,
          updated_at = now,
      )

      val productId = dbQuery.lastInsertRowId().executeAsOne()

      dbQuery.insertOrIgnoreStock(
          product_id = productId,
          current_qty = product.stock,
          initial_qty = product.stock,
          low_stock_threshold = 3L,
          updated_at = now,
      )

      val qrPayload =
          QrPayload(
              id = productId.toString(),
              name = product.name,
              price = product.price.toString(),
              cost = product.cost.toString(),
          )

      val qr = qrPayload.toQrString()

      dbQuery.updateProduct(
          id = productId,
          name = product.name,
          price = product.price,
          cost = product.cost,
          emoji = product.emoji ?: "",
          isActive = if (product.isActive) 1L else 0L,
          updatedAt = now,
          qrString = qr,
      )

      product.copy(
          id = productId,
          qrString = qr,
          createdAt = if (product.createdAt == 0L) now else product.createdAt,
          updatedAt = now,
      )
    }
  }

  override suspend fun getAllProducts(): List<Product> {
    return dbQuery.selectAllProducts(::mapProductSelecting).executeAsList()
  }

  override suspend fun getProductById(id: Long): Product? {
    return dbQuery.selectProductById(id, ::mapProductSelecting).executeAsOneOrNull()
  }

  override suspend fun getProductByStock(id: Long): Boolean {
    return dbQuery.findProductByStock(id).executeAsOne()
  }

  override suspend fun getProductByName(name: String): Boolean {
    return dbQuery.findProductByName(name).executeAsOne()
  }

  override suspend fun deleteProduct(id: Long) {
    dbQuery.deleteProduct(id = id)
  }

  override suspend fun decrementStock(productId: Long, qty: Long) {
    val now = Clock.System.now().toEpochMilliseconds()
    dbQuery.decrementStock(qty = qty, updatedAt = now, productId = productId)
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
