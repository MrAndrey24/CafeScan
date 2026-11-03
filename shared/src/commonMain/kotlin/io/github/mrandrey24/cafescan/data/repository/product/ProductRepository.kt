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

class ProductRepository(private val localProductDataSource: IProductLocalDataSource) :
    IProductRepository {

  override fun saveProduct(product: Product): Product {
    return localProductDataSource.createProduct(product)
  }

  override suspend fun retrieveAllProducts(): List<Product> {
    return localProductDataSource.getAllProducts()
  }

  override suspend fun retrieveProductById(id: Long): Product? {
    return localProductDataSource.getProductById(id)
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
