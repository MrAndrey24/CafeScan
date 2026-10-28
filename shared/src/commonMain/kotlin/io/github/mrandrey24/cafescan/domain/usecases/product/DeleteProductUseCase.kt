package io.github.mrandrey24.cafescan.domain.usecases.product

import io.github.mrandrey24.cafescan.data.repository.product.IProductRepository

class DeleteProductUseCase(
    private val productRepository: IProductRepository
) {
    suspend operator fun invoke(productId: Long) {
        productRepository.deleteProduct(productId)
    }
}
