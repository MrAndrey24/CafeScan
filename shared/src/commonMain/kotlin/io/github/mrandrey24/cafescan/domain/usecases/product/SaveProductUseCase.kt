package io.github.mrandrey24.cafescan.domain.usecases.product

import io.github.mrandrey24.cafescan.data.repository.product.IProductRepository
import io.github.mrandrey24.cafescan.domain.entities.Product

class SaveProductUseCase(private val productRepository: IProductRepository) {
    operator fun invoke(product: Product): Product {
        return productRepository.saveProduct(product)
    }
}
