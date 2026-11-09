/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.domain.usecases.inventory

import io.github.mrandrey24.cafescan.data.repository.inventory.IInventoryRepository
import io.github.mrandrey24.cafescan.domain.entities.StockItem

class GetAllActiveWithStockUseCase(private val inventoryRepository: IInventoryRepository) {
  suspend operator fun invoke(): List<StockItem> = inventoryRepository.getAllActiveWithStock()
}
