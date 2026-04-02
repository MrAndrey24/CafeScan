package io.github.mrandrey24.cafescan.domain.product

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val cost: Double,
    val emoji: String = "",
    val isActive: Boolean = true,
)
