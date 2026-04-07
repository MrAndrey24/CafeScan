package io.github.mrandrey24.cafescan.domain.entities

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val cost: Double,
    val emoji: String = "",
    val isActive: Boolean = true,
) {
  val margin: Double
    get() = price - cost

  val marginPercent: Int
    get() = if (price > 0) ((margin / price) * 100).toInt() else 0
}
