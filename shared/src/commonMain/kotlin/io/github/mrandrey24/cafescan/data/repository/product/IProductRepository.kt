/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.data.repository.product

import io.github.mrandrey24.cafescan.domain.entities.Product

interface IProductRepository {
  fun saveProduct(product: Product): Product

  suspend fun retrieveAllProducts(): List<Product>

  suspend fun retrieveProductById(id: Long): Product?

  suspend fun isNameExists(name: String): Boolean

  suspend fun deleteProduct(id: Long)

  suspend fun decrementStock(productId: Long, qty: Long)
}
