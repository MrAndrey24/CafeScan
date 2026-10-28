package io.github.mrandrey24.cafescan.domain.usecases.product

import io.github.mrandrey24.cafescan.data.repository.product.IProductRepository

class CheckProductNameExistsUseCase(private val productRepository: IProductRepository) {
    suspend operator fun invoke(name: String): Boolean {
        return productRepository.isNameExists(name)
    }
}
