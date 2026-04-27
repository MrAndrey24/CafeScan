package io.github.mrandrey24.cafescan.data.local.product

import io.github.mrandrey24.cafescan.domain.entities.Product

interface IProductLocalDataSource {
    fun createProduct(product: Product): Long
    suspend fun getAllProducts(): List<Product>
    suspend fun getProductById(id: Long): Product?
    suspend fun getProductByName(name: String): Boolean
}
