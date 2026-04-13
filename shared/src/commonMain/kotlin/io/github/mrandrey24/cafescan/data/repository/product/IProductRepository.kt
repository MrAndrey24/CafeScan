package io.github.mrandrey24.cafescan.data.repository.product

import io.github.mrandrey24.cafescan.domain.entities.Product

interface IProductRepository {
    fun saveProduct(product: Product): Long
    fun retrieveAllProducts(): List<Product>
    fun retrieveProductById(id: Long): Product?
}
