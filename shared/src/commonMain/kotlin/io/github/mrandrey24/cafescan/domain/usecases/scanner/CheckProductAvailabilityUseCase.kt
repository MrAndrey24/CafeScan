/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.domain.usecases.scanner

import io.github.mrandrey24.cafescan.data.repository.product.IProductRepository

class CheckProductAvailabilityUseCase(private val productRepository: IProductRepository) {
  suspend operator fun invoke(productId: Long): Boolean {
    return productRepository.retrieveProductByStock(productId)
  }
}
