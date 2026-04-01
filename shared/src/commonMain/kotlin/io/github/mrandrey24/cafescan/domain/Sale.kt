package io.github.mrandrey24.cafescan.domain

data class Sale(
    val id: Int,
    val productId: Int,
    val quantity: Int,
    val timestamp: Long,
    val totalAmount: Double,
)
