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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductRepository(
    private val localProductDataSource: IProductLocalDataSource,
    private val remoteProductRemoteDataSource: IProductRemoteDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : IProductRepository {

  override suspend fun createProduct(product: Product): Product {
    val savedLocalProduct = localProductDataSource.createProduct(product)
    runCatching { remoteProductRemoteDataSource.saveProduct(savedLocalProduct) }
        .onFailure { exception ->
          println("Failed saving product to remote: $exception")
        }
    return savedLocalProduct
  }

  override suspend fun retrieveAllProducts(): Flow<List<Product>> = flow {
    val cachedProducts = localProductDataSource.getAllProducts()
    if (cachedProducts.isNotEmpty()) {
      emit(cachedProducts)
    }

    try {
      remoteProductRemoteDataSource
          .retrieveAllProducts()
          .catch { exception ->
            println("Failed fetching products from network. Using local cache: $exception")
          }
          .collect { remoteProducts ->
            localProductDataSource.clearAndUpdateProducts(remoteProducts)
            emit(localProductDataSource.getAllProducts())
          }
    } catch (exception: Exception) {
      println("Failed setting up remote product listener: $exception")
    }
  }
      .flowOn(defaultDispatcher)

  override suspend fun retrieveProductById(id: Long): Product? {
    return localProductDataSource.getProductById(id)
  }

  override suspend fun retrieveProductByStock(id: Long): Boolean {
    return localProductDataSource.getProductByStock(id)
  }

  override suspend fun isNameExists(name: String): Boolean {
    return localProductDataSource.getProductByName(name)
  }

  override suspend fun deleteProduct(id: Long) {
    localProductDataSource.deleteProduct(id)
  }

  override suspend fun decrementStock(productId: Long, qty: Long) {
    localProductDataSource.decrementStock(productId, qty)
  }
}
