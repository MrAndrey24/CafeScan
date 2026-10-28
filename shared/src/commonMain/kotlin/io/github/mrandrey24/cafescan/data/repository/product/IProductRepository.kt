package io.github.mrandrey24.cafescan.data.repository.product

import io.github.mrandrey24.cafescan.domain.entities.Product

interface IProductRepository {
    fun saveProduct(product: Product): Product
    suspend fun retrieveAllProducts(): List<Product>
    suspend fun retrieveProductById(id: Long): Product?
    suspend fun isNameExists(name: String): Boolean
}
