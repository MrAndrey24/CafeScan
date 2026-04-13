package io.github.mrandrey24.cafescan.data.local.product

import io.github.mrandrey24.cafescan.domain.entities.Product

interface IProductLocalDataSource {
    fun createProduct(product: Product): Long
    fun getAllProducts(): List<Product>
    fun getProductById(id: Long): Product?
}
