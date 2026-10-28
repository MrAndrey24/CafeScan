package io.github.mrandrey24.cafescan.domain.usecases.inventory

import io.github.mrandrey24.cafescan.data.repository.inventory.IInventoryRepository
import io.github.mrandrey24.cafescan.domain.entities.StockItem

class GetAllActiveWithStockUseCase(
    private val inventoryRepository: IInventoryRepository
) {
    suspend operator fun invoke() : List<StockItem> = inventoryRepository.getAllActiveWithStock()
}
