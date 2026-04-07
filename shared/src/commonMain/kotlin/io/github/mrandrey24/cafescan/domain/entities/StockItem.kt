package io.github.mrandrey24.cafescan.domain.entities

data class StockItem(
    val productId: String,
    val currentQty: Int,
    val initialQty: Int,
    val lowStockThreshold: Int = 3,
) {
  val status: StockStatus
    get() =
        when {
          currentQty <= 0 -> StockStatus.OUT_OF_STOCK
          currentQty <= lowStockThreshold -> StockStatus.CRITICAL
          currentQty <= lowStockThreshold * 2 -> StockStatus.LOW
          else -> StockStatus.HEALTHY
        }

  val levelPercent: Float
    get() = if (initialQty > 0) (currentQty.toFloat() / initialQty).coerceIn(0f, 1f) else 0f
}
