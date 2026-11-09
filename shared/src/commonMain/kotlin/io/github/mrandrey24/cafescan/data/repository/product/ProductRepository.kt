/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.data.repository.product

import io.github.mrandrey24.cafescan.data.local.product.IProductLocalDataSource
import io.github.mrandrey24.cafescan.domain.entities.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class ProductRepository(
    private val localProductDataSource: IProductLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : IProductRepository {

  override suspend fun saveProduct(product: Product): Product =
      withContext(ioDispatcher) { localProductDataSource.createProduct(product) }

  override suspend fun retrieveAllProducts(): List<Product> =
      withContext(ioDispatcher) { localProductDataSource.getAllProducts() }

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
