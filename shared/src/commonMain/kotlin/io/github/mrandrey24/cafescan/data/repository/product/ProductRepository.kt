package io.github.mrandrey24.cafescan.data.repository.product

import io.github.mrandrey24.cafescan.data.local.product.IProductLocalDataSource
import io.github.mrandrey24.cafescan.domain.entities.Product

class ProductRepository(private val localProductDataSource: IProductLocalDataSource) : IProductRepository {


    override fun saveProduct(product: Product): Long {
       return localProductDataSource.createProduct(product)
    }

    override fun retrieveAllProducts(): List<Product> {
        return localProductDataSource.getAllProducts()
    }

    override fun retrieveProductById(id: Long): Product? {
        return localProductDataSource.getProductById(id)
    }

}
