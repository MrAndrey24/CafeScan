package io.github.mrandrey24.cafescan.domain.usecases

import io.github.mrandrey24.cafescan.data.repository.inventory.IInventoryRepository

class UpdateStockUseCase(
    private val inventoryRepository: IInventoryRepository
) {
    suspend operator fun invoke(productId: Long, currentQty: Int, price: Double) {
        inventoryRepository.updateStockAndPrice(productId, currentQty, price)
    }
}
