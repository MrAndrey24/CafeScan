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
package io.github.mrandrey24.cafescan.data.remote.inventory

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import io.github.mrandrey24.cafescan.domain.entities.StockItem
import kotlin.time.Clock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InventoryRemoteDataSource : IInventoryRemoteDataSource {

  override suspend fun updateStockAndPriceRemote(productId: Long, currentQty: Int, price: Double) {
    val map =
        mapOf(
            "productId" to productId,
            "currentQty" to currentQty,
            "price" to price,
            "updatedAt" to Clock.System.now().toEpochMilliseconds(),
        )
    Firebase.firestore.collection(INVENTORY_COLLECTION).document(productId.toString()).set(map)
  }

  override fun getAllActiveWithStockRemote(): Flow<List<StockItem>> {
    return Firebase.firestore.collection(INVENTORY_COLLECTION).snapshots().map { querySnapshot ->
      querySnapshot.documents.mapNotNull { doc ->
        try {
          doc.data<StockItem>()
        } catch (_: Exception) {
          null
        }
      }
    }
  }

  private companion object {
    const val INVENTORY_COLLECTION = "inventory"
  }
}
