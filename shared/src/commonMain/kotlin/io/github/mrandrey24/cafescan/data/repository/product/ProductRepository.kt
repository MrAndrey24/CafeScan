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
package io.github.mrandrey24.cafescan.data.repository.product

import io.github.mrandrey24.cafescan.data.local.product.IProductLocalDataSource
import io.github.mrandrey24.cafescan.data.remote.product.IProductRemoteDataSource
import io.github.mrandrey24.cafescan.domain.entities.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class ProductRepository(
    private val localProductDataSource: IProductLocalDataSource,
    private val remoteProductRemoteDataSource: IProductRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : IProductRepository {

  override suspend fun saveProduct(product: Product): Product =
      withContext(ioDispatcher) {
        val savedLocalProduct = localProductDataSource.createProduct(product = product)
        runCatching { remoteProductRemoteDataSource.saveProduct(product = savedLocalProduct) }
        savedLocalProduct
      }

  override fun retrieveAllProducts(): Flow<List<Product>> =
      remoteProductRemoteDataSource
          .retrieveAllProducts()
          .onEach { products ->
            products.forEach { product -> localProductDataSource.createProduct(product) }
          }
          .flowOn(ioDispatcher)
          .catch { _ ->
            val cachedProducts = localProductDataSource.getAllProducts()
            if (cachedProducts.isNotEmpty()) {
              emit(cachedProducts)
            }
          }

  override suspend fun retrieveProductById(id: Long): Product? =
      withContext(ioDispatcher) { localProductDataSource.getProductById(id) }

  override suspend fun retrieveProductByStock(id: Long): Boolean =
      withContext(ioDispatcher) { localProductDataSource.getProductByStock(id) }

  override suspend fun isNameExists(name: String): Boolean =
      withContext(ioDispatcher) { localProductDataSource.getProductByName(name) }

  override suspend fun deleteProduct(id: Long): Unit =
      withContext(ioDispatcher) { localProductDataSource.deleteProduct(id) }

  override suspend fun decrementStock(productId: Long, qty: Long): Unit =
      withContext(ioDispatcher) { localProductDataSource.decrementStock(productId, qty) }
}
