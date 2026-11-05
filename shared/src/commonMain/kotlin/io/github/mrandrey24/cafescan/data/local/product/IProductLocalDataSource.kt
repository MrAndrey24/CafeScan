/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.data.local.product

import io.github.mrandrey24.cafescan.domain.entities.Product

interface IProductLocalDataSource {
  fun createProduct(product: Product): Product

  suspend fun getAllProducts(): List<Product>

  suspend fun getProductById(id: Long): Product?

  suspend fun getProductByStock(id: Long): Boolean

  suspend fun getProductByName(name: String): Boolean

  suspend fun deleteProduct(id: Long)

  suspend fun decrementStock(productId: Long, qty: Long)
}
