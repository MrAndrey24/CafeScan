package io.github.mrandrey24.cafescan.domain.sale

import io.github.mrandrey24.cafescan.domain.product.Product

data class OrderItem(val product: Product, val quantity: Int) {
  val subtotal: Double
    get() = product.price * quantity

  val profit: Double
    get() = (product.price - product.cost) * quantity
}
