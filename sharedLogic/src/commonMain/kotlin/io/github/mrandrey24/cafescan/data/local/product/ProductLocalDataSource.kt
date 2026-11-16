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
package io.github.mrandrey24.cafescan.data.local.product

import io.github.mrandrey24.cafescan.data.local.AppDatabase
import io.github.mrandrey24.cafescan.domain.entities.Product
import io.github.mrandrey24.cafescan.domain.entities.QrPayload
import kotlin.time.Clock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class ProductLocalDataSource(
    database: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : IProductLocalDataSource {
  private val dbQuery = database.appDatabaseQueries

  override suspend fun createProduct(product: Product): Product =
      withContext(ioDispatcher) {
        dbQuery.transactionWithResult {
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

  override suspend fun getAllProducts(): List<Product> =
      withContext(ioDispatcher) { dbQuery.selectAllProducts(::mapProductSelecting).executeAsList() }

  override suspend fun getProductById(id: Long): Product? =
      withContext(ioDispatcher) {
        dbQuery.selectProductById(id, ::mapProductSelecting).executeAsOneOrNull()
      }

  override suspend fun getProductByStock(id: Long): Boolean =
      withContext(ioDispatcher) { dbQuery.findProductByStock(id).executeAsOne() }

  override suspend fun getProductByName(name: String): Boolean =
      withContext(ioDispatcher) { dbQuery.findProductByName(name).executeAsOne() }

  override suspend fun deleteProduct(id: Long): Unit =
      withContext(ioDispatcher) { dbQuery.deleteProduct(id = id) }

  override suspend fun decrementStock(productId: Long, qty: Long): Unit =
      withContext(ioDispatcher) {
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
