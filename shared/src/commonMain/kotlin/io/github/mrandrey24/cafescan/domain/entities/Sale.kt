package io.github.mrandrey24.cafescan.domain.entities

data class Sale(
    val id: String,
    val items: List<OrderItem>,
    val timestamp: Long
) {
    val total: Double  get() = items.sumOf { it.subtotal }
    val profit: Double get() = items.sumOf { it.profit }
    val itemCount: Int get() = items.sumOf { it.quantity }
}
