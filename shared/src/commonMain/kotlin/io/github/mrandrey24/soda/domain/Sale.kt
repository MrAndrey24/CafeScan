package io.github.mrandrey24.soda.domain

data class Sale(val id: Int, val productId: Int, val quantity: Int, val timestamp: Long, val totalAmount: Double)
