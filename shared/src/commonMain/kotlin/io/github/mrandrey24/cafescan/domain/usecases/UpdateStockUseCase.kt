/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.domain.usecases

import io.github.mrandrey24.cafescan.data.repository.inventory.IInventoryRepository

class UpdateStockUseCase(private val inventoryRepository: IInventoryRepository) {
  suspend operator fun invoke(productId: Long, currentQty: Int, price: Double) {
    inventoryRepository.updateStockAndPrice(productId, currentQty, price)
  }
}
