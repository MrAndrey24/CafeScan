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
package io.github.mrandrey24.cafescan.data.repository.inventory

import io.github.mrandrey24.cafescan.data.local.inventory.IInventoryLocalDataSource
import io.github.mrandrey24.cafescan.data.remote.inventory.IInventoryRemoteDataSource
import io.github.mrandrey24.cafescan.domain.entities.StockItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class InventoryRepository(
    private val inventoryLocalDataSource: IInventoryLocalDataSource,
    private val inventoryRemoteDataSource: IInventoryRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : IInventoryRepository {

  override fun getAllActiveWithStockFlow(): Flow<List<StockItem>> =
      inventoryRemoteDataSource
          .getAllActiveWithStockRemote()
          .onEach { items ->
            items.forEach { item ->
              inventoryLocalDataSource.updateStockAndPrice(
                  item.product.id,
                  item.currentQty,
                  item.product.price,
              )
            }
          }
          .flowOn(defaultDispatcher)
          .catch { _ ->
            val cachedItems = inventoryLocalDataSource.getAllActiveWithStock()
            if (cachedItems.isNotEmpty()) {
              emit(cachedItems)
            }
          }

  override suspend fun updateStockAndPrice(
      productId: Long,
      currentQty: Int,
      price: Double,
  ): Unit =
      withContext(ioDispatcher) {
        inventoryLocalDataSource.updateStockAndPrice(productId, currentQty, price)
        runCatching {
          inventoryRemoteDataSource.updateStockAndPriceRemote(productId, currentQty, price)
        }
      }
}
