package io.github.mrandrey24.cafescan.domain.sale

data class Sale(
    val id: String,
    val items: List<OrderItem>,
    val timestamp: Long,
    val total: Double = items.sumOf { it.subtotal },
    val profit: Double = items.sumOf { it.profit },
)
