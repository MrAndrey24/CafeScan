/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.domain.entities

data class Sale(val id: String, val items: List<OrderItem>, val timestamp: Long) {
  val total: Double
    get() = items.sumOf { it.subtotal }

  val profit: Double
    get() = items.sumOf { it.profit }

  val itemCount: Int
    get() = items.sumOf { it.quantity }
}
