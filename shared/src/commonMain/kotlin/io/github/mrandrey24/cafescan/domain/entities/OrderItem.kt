/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.domain.entities

data class OrderItem(val product: Product, var quantity: Int) {
  val subtotal: Double
    get() = product.price * quantity

  val profit: Double
    get() = product.margin * quantity
}
