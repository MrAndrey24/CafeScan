/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Long = 0,
    val name: String,
    val price: Double,
    val cost: Double,
    val emoji: String? = "",
    val qrString: String? = "",
    val isActive: Boolean = true,
    val stock: Long = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
) {
  val margin: Double
    get() = price - cost

  val marginPercent: Int
    get() = if (price > 0) ((margin / price) * 100).toInt() else 0
}
